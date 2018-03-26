package com.easynfc.writer.url;

import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;

/**
 * Created by pablorojas on 26/2/18.
 */

public interface UrlWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();

        void showInserted();

        void showMessageError();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String text);

        void disableForegroundDispatch();

        void saveTag(String content);

        void loadTag(long tagId, LoadUrlTagCallback loadUrlTagCallback);

        void emulateTag(String url);
    }

    interface LoadUrlTagCallback {
        void onTagLoaded(UrlTag urlTag);
        void onDatanotAvailable();
    }
}
