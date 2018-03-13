package com.easynfc.writer.url;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;

/**
 * Created by pablorojas on 26/2/18.
 */

public interface UrlWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String text);

        void disableForegroundDispatch();

        void saveTag(String content);
    }
}
