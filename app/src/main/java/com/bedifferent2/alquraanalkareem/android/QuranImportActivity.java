package com.bedifferent2.alquraanalkareem.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bedifferent2.alquraanalkareem.android.dao.BookmarkData;
import com.bedifferent2.alquraanalkareem.android.presenter.QuranImportPresenter;

import javax.inject.Inject;

public class QuranImportActivity extends AppCompatActivity implements
    ActivityCompat.OnRequestPermissionsResultCallback {
  private AlertDialog mDialog;
  @Inject QuranImportPresenter mPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    QuranApplication quranApp = ((QuranApplication) getApplication());
    quranApp.refreshLocale(this, false);
    super.onCreate(savedInstanceState);
    quranApp.getApplicationComponent().inject(this);

  }

  @Override
  protected void onPause() {
    mPresenter.unbind(this);
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.bind(this);
  }

  @Override
  protected void onDestroy() {
    mPresenter.unbind(this);
    if (mDialog != null) {
      mDialog.dismiss();
    }
    super.onDestroy();
  }

  public boolean isShowingDialog() {
    return mDialog != null;
  }

  public void showImportConfirmationDialog(final BookmarkData bookmarkData) {
    String message = getString(R.string.import_data_and_override,
        bookmarkData.getBookmarks().size(),
        bookmarkData.getTags().size());
    AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton(R.string.import_data,
            (dialog, which) -> mPresenter.importData(bookmarkData))
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> finish())
        .setOnCancelListener(dialog -> finish());
    mDialog = builder.show();
  }

  public void showImportComplete() {
    Toast.makeText(QuranImportActivity.this,
        R.string.import_successful, Toast.LENGTH_LONG).show();
    finish();
  }

  public void showError() {
    showErrorInternal(R.string.import_data_error);
  }

  public void showPermissionsError() {
    showErrorInternal(R.string.import_data_permissions_error);
  }

  private void showErrorInternal(int messageId) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setMessage(messageId)
        .setPositiveButton(android.R.string.ok, (dialog, which) -> finish());
    mDialog = builder.show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    mPresenter.onPermissionsResult(requestCode, grantResults);
  }
}
