package com.easynfc.writer.format;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface FormatWriterContract {

    interface View extends BaseView<Presenter> {

        void onTagWritten();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void disableForegroundDispatch();

        void formatTag(Intent intent);
    }
}

