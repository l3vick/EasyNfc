package com.easynfc.writer.lock;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface LockWriterContract {

    interface View extends BaseView<Presenter> {
        void onTagWritten();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void disableForegroundDispatch();

        void lockTag(Intent intent);
    }
}

