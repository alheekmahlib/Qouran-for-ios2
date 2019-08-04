package com.bedifferent2.alquraanalkareem.android.model.quran;

import android.graphics.RectF;
import android.support.v4.util.Pair;

import com.bedifferent2.alquraanalkareem.android.common.AyahBounds;
import com.bedifferent2.alquraanalkareem.android.data.AyahInfoDatabaseHandler;
import com.bedifferent2.alquraanalkareem.android.data.AyahInfoDatabaseProvider;
import com.bedifferent2.alquraanalkareem.android.di.ActivityScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class CoordinatesModel {
  private static final int PIXEL_THRESHOLD = 10;

  private final AyahInfoDatabaseProvider ayahInfoDatabaseProvider;

  @Inject
  CoordinatesModel(AyahInfoDatabaseProvider ayahInfoDatabaseProvider) {
    this.ayahInfoDatabaseProvider = ayahInfoDatabaseProvider;
  }

  public Observable<Pair<Integer, RectF>> getPageCoordinates(Integer... pages) {
    AyahInfoDatabaseHandler database = ayahInfoDatabaseProvider.getAyahInfoHandler();
    if (database == null) {
      return Observable.error(new NoSuchElementException("No AyahInfoDatabaseHandler found!"));
    }

    return Observable.fromArray(pages)
        .map(page -> new Pair<>(page, database.getPageBounds(page)))
        .subscribeOn(Schedulers.computation());
  }

  public Observable<Pair<Integer, Map<String, List<AyahBounds>>>> getAyahCoordinates(
      Integer... pages) {
    AyahInfoDatabaseHandler database = ayahInfoDatabaseProvider.getAyahInfoHandler();
    if (database == null) {
      return Observable.error(new NoSuchElementException("No AyahInfoDatabaseHandler found!"));
    }

    return Observable.fromArray(pages)
        .map(page -> new Pair<>(page, database.getVersesBoundsForPage(page)))
        .map(pair -> new Pair<>(pair.first, normalizeMap(pair.second)))
        .subscribeOn(Schedulers.computation());
  }

  private Map<String, List<AyahBounds>> normalizeMap(Map<String, List<AyahBounds>> original) {
    Map<String, List<AyahBounds>> normalizedMap = new HashMap<>();
    final Set<String> keys = original.keySet();
    for (String key : keys) {
      List<AyahBounds> normalBounds = original.get(key);
      normalizedMap.put(key, normalizeAyahBounds(key, normalBounds));
    }
    return normalizedMap;
  }

  private List<AyahBounds> normalizeAyahBounds(String key, List<AyahBounds> ayahBounds) {
    final int total = ayahBounds.size();
    if (total < 2) {
      return ayahBounds;
    } else if (total < 3) {
      return consolidate(ayahBounds.get(0), ayahBounds.get(1));
    } else {
      AyahBounds middle = ayahBounds.get(1);
      for (int i = 2; i < total - 1; i++) {
        middle.engulf(ayahBounds.get(i));
      }

      List<AyahBounds> top = consolidate(ayahBounds.get(0), middle);
      final int topSize = top.size();
      // the first parameter is essentially middle (after its consolidation with the top line)
      List<AyahBounds> bottom = consolidate(top.get(topSize - 1), ayahBounds.get(total - 1));

      List<AyahBounds> result = new ArrayList<>();
      if (topSize == 1) {
        return bottom;
      } else if (topSize + bottom.size() > 4) {
        result.addAll(top);
        result.addAll(bottom);
        return result;
      } else {
        // re-consolidate top and middle again, since middle may have changed
        top = consolidate(top.get(0), bottom.get(0));
        result.addAll(top);
        if (bottom.size() > 1) {
          result.add(bottom.get(1));
        }
        return result;
      }
    }
  }

  private List<AyahBounds> consolidate(AyahBounds top, AyahBounds bottom) {
    final RectF firstRect = top.getBounds();
    final RectF lastRect = bottom.getBounds();

    AyahBounds middle = null;

    // only 2 lines - let's see if any of them are full lines
    boolean firstIsFullLine = Math.abs(firstRect.right - lastRect.right) < PIXEL_THRESHOLD;
    boolean secondIsFullLine = Math.abs(firstRect.left - lastRect.left) < PIXEL_THRESHOLD;
    if (firstIsFullLine && secondIsFullLine) {
      top.engulf(bottom);
      return Collections.singletonList(top);
    } else if (firstIsFullLine) {
      lastRect.top = firstRect.bottom;
      float bestStartOfLine = Math.max(firstRect.right, lastRect.right);
      firstRect.right = bestStartOfLine;
      lastRect.right = bestStartOfLine;

      top = top.withBounds(firstRect);
      bottom = bottom.withBounds(lastRect);
    } else if (secondIsFullLine) {
      firstRect.bottom = lastRect.top;
      float bestEndOfLine = Math.min(firstRect.left, lastRect.left);
      firstRect.left = bestEndOfLine;
      lastRect.left = bestEndOfLine;

      top = top.withBounds(firstRect);
      bottom = bottom.withBounds(lastRect);
    } else {
      // neither one is a full line, let's generate a middle entry to join them if they have
      // anything in common (i.e. any part of them intersects)
      if (lastRect.left < firstRect.right) {
        RectF middleBounds = new RectF(lastRect.left,
            /* top= */ firstRect.bottom,
            firstRect.right,
            /* bottom= */ lastRect.top);
        middle = new AyahBounds(top.getLine(), top.getPosition(), middleBounds);
      }
    }

    List<AyahBounds> result = new ArrayList<>();
    result.add(top);
    if (middle != null) {
      result.add(middle);
    }
    result.add(bottom);
    return result;
  }
}
