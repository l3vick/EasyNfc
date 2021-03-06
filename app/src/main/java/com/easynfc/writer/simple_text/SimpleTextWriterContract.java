package com.easynfc.writer.simple_text;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.TextTag;

/**
 * Created by pablorojas on 26/2/18.
 */

public interface SimpleTextWriterContract {

    interface View extends BaseView<Presenter> {
        void onTagWritten();

        void insertSuccess();

        void showMessageError();

        void updatedSuccess();
    }

    interface Presenter extends BasePresenter {

        void writeTag(Intent intent, String text);

        void enableForegroundDispatch();

        void disableForegroundDispatch();

        void saveTag(String content);

        void loadTag(long timestamp, LoadTextTagCallback loadTextTagCallback);

        void emulateTag(String text);

        void updateTag(long tagId, String content);
    }

    interface LoadTextTagCallback {
        void onTagLoaded(TextTag textTag);
        void onDatanotAvailable();
    }
}
