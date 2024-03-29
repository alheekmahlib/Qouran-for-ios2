package com.bedifferent2.alquraanalkareem.android.ui.helpers;

import com.bedifferent2.alquraanalkareem.android.data.SuraAyah;

public interface AyahSelectedListener {

  enum EventType { SINGLE_TAP, LONG_PRESS, DOUBLE_TAP }

  /** Return true to receive the ayah info along with the
   * click event, false to receive just the event type */
  boolean isListeningForAyahSelection(EventType eventType);

  /** Click event with ayah info and highlighter passed */
  boolean onAyahSelected(EventType eventType,
          SuraAyah suraAyah, AyahTracker tracker);

  /** General click event without ayah info */
  boolean onClick(EventType eventType);

}
