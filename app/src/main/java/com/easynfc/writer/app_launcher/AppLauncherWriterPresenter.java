package com.easynfc.writer.app_launcher;

import android.content.Intent;
import android.util.Log;

import com.easynfc.util.NfcUtils;

/**
 * Created by pablorojas on 28/2/18.
 */

public class AppLauncherWriterPresenter implements AppLauncherWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private AppLauncherWriterContract.View view;
    private static String TAG = "AppLauncherWriterPresenter";

    public AppLauncherWriterPresenter(AppLauncherWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "AppLauncherWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "AppLauncherWriterPresenter: Users Repository can't be null.");
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void enableForegroundDispatch() {

    }

    @Override
    public void writeTag(Intent intent, String text) {

    }

    @Override
    public void disableForegroundDispatch() {

    }
}
