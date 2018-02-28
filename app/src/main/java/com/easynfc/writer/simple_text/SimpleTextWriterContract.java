package com.easynfc.writer.simple_text;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;

/**
 * Created by pablorojas on 26/2/18.
 */

public interface SimpleTextWriterContract {

    interface View extends BaseView<Presenter> {
        void OnTagWritten();
    }

    interface Presenter extends BasePresenter {

        void writeTag(Intent intent, String text);

        void enableForegroundDispatch();

        void disableForegroundDispatch();
    }
}
