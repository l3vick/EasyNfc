package com.easynfc.writer.Url;

import android.content.Intent;
import android.util.Log;

import com.easynfc.util.NfcUtils;

/**
 * Created by pablorojas on 26/2/18.
 */

public class UrlWriterPresenter implements UrlWriterContract.Presenter{

    private static final String TAG = "UrlWriterPresenter";
    private UrlWriterContract.View view;
    private NfcUtils nfcUtils;


    public UrlWriterPresenter(UrlWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "TagsMenuPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "TagsMenuPresenter: Users Repository can't be null.");
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
