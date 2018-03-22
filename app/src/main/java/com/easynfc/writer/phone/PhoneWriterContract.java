package com.easynfc.writer.phone;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.PhoneTag;
import com.easynfc.data.TextTag;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface PhoneWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();

        void showAddedSuccess();

        void showMessageError();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String text);

        void disableForegroundDispatch();

        void saveTag(String phone);

        void loadTag(long tagId, LoadPhoneTagCallback loadPhoneTagCallback);

        void emulateTag(String phone);
    }

    interface LoadPhoneTagCallback {
        void onTagLoaded(PhoneTag phoneTag);
        void onDatanotAvailable();
    }
}

