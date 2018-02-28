package com.easynfc.writer.sms;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface SmsWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String text);

        void disableForegroundDispatch();
    }
}

