package com.bedifferent2.alquraanalkareem.android.service.util;

import android.os.Parcel;

import com.bedifferent2.alquraanalkareem.android.data.SuraAyah;

public class StreamingAudioRequest extends AudioRequest {

  public StreamingAudioRequest(String baseUrl, SuraAyah verse) {
    super(baseUrl, verse);
  }

  protected StreamingAudioRequest(Parcel in) {
    super(in);
  }

  @Override
  public boolean haveSuraAyah(int sura, int ayah) {
    // for streaming, we (theoretically) always "have" the sura and ayah
    return true;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
  }

  public static final Creator<StreamingAudioRequest> CREATOR
      = new Creator<StreamingAudioRequest>() {
    @Override
    public StreamingAudioRequest createFromParcel(Parcel source) {
      return new StreamingAudioRequest(source);
    }

    @Override
    public StreamingAudioRequest[] newArray(int size) {
      return new StreamingAudioRequest[size];
    }
  };
}
