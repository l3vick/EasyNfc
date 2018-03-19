package com.easynfc.writer.app_launcher;

import android.app.Activity;
import android.content.Intent;

import com.easynfc.BasePresenter;
import com.easynfc.BaseView;
import com.easynfc.data.AarTag;
import com.easynfc.data.TextTag;

import java.util.List;

/**
 * Created by pablorojas on 28/2/18.
 */

public interface AppLauncherWriterContract {

    interface View extends BaseView<Presenter> {

        void OnTagWritten();

        void showMessageSuccess();

        void showMessageError();
    }

    interface Presenter extends BasePresenter {

        void enableForegroundDispatch();

        void writeTag(Intent intent, String text);

        void disableForegroundDispatch();

        List<String> getInstalledPackageNameList(Activity activity);

        void saveTag(String content);

        void loadTag(long tagId, LoadAarTagCallback loadAarTagCallback);
    }

    interface OnAarItemClickedCallback {
        void OnSuccess(String aar);
    }

    interface LoadAarTagCallback {
        void onTagLoaded(AarTag aarTag);
        void onDatanotAvailable();
    }
}

