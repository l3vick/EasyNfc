package com.easynfc.tagsmenu;

import android.util.Log;

import com.easynfc.data.model.TagMenu;

import java.util.List;

/**
 * Created by pablorojas on 21/2/18.
 */

public class TagsMenuPresenter implements TagsMenuContract.Presenter {

    private static final String TAG = "TagsMenuPresenter";
    private TagsMenuContract.View view;


    public TagsMenuPresenter(TagsMenuContract.View view) {
        if (view != null) {
            this.view = view;
            view.setPresenter(this);
        } else {
            Log.d(TAG, "View can't be null.");
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {
        this.view = null;
    }
}
