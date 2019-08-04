package com.bedifferent2.alquraanalkareem.android.presenter;

public interface Presenter<T> {
  void bind(T what);
  void unbind(T what);
}
