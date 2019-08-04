package com.bedifferent2.alquraanalkareem.android.presenter.bookmark;

import com.bedifferent2.alquraanalkareem.android.dao.Tag;
import com.bedifferent2.alquraanalkareem.android.model.bookmark.BookmarkModel;
import com.bedifferent2.alquraanalkareem.android.presenter.Presenter;
import com.bedifferent2.alquraanalkareem.android.ui.fragment.AddTagDialog;

import javax.inject.Inject;

public class AddTagDialogPresenter implements Presenter<AddTagDialog> {
  private BookmarkModel mBookmarkModel;

  @Inject
  AddTagDialogPresenter(BookmarkModel bookmarkModel) {
    mBookmarkModel = bookmarkModel;
  }

  public void addTag(String tagName) {
    mBookmarkModel.addTagObservable(tagName)
        .subscribe();
  }

  public void updateTag(Tag tag) {
    mBookmarkModel.updateTag(tag)
        .subscribe();
  }

  @Override
  public void bind(AddTagDialog dialog) {
  }

  @Override
  public void unbind(AddTagDialog dialog) {
  }
}
