package com.bedifferent2.alquraanalkareem.android.model.bookmark;

import com.bedifferent2.alquraanalkareem.android.dao.Tag;
import com.bedifferent2.alquraanalkareem.android.ui.helpers.QuranRow;

import java.util.List;
import java.util.Map;

public class BookmarkResult {

  public final List<QuranRow> rows;
  public final Map<Long, Tag> tagMap;

  public BookmarkResult(List<QuranRow> rows, Map<Long, Tag> tagMap) {
    this.rows = rows;
    this.tagMap = tagMap;
  }
}
