package com.easynfc.writer.sms;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.SmsTag;
import com.easynfc.data.TextTag;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface SmsWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();

        void showInserted();

        void showMessageError();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String number, String text);

        void disableForegroundDispatch();

        void saveTag(String s, String s1);

        void loadTag(long tagId, LoadSmsTagCallback loadSmsTagCallback);

        void emulateTag(String number, String text);
    }

    interface LoadSmsTagCallback {
        void onTagLoaded(SmsTag smsTag);
        void onDatanotAvailable();
    }
}

