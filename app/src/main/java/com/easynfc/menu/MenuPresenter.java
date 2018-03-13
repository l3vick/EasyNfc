package com.easynfc.menu;

import android.util.Log;

/**
 * Created by pablorojas on 20/2/18.
 */

public class MenuPresenter implements MenuContract.Presenter {

    private static final String TAG = "MenuPresenter";
    private MenuContract.View view;


    public MenuPresenter(MenuContract.View view) {
        if (view != null) {
            this.view = view;
            view.setPresenter(this);
        } else {
            Log.d(TAG, "MenuPresenter: View can't be null.");
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
    }
}
