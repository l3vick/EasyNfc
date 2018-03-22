package com.easynfc.writer.email;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.EmailTag;
import com.easynfc.data.TextTag;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface EmailWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();

        void showAddedSuccess();

        void showMessageError();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String text);

        void disableForegroundDispatch();

        void saveTag(String email);

        void loadTag(long tagId, LoadEmailTagCallback loadEmailTagCallback);

        void emulateTag(String email);
    }

    interface LoadEmailTagCallback {
        void onTagLoaded(EmailTag emailTag);
        void onDatanotAvailable();
    }
}

