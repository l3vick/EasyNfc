package com.easynfc.writer.format;

import android.content.Intent;
import android.util.Log;

import com.easynfc.util.NfcUtils;
import com.easynfc.writer.location.LocationWriterContract;

/**
 * Created by pablorojas on 28/2/18.
 */

public class FormatWriterPresenter implements FormatWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private FormatWriterContract.View view;
    private static String TAG = "FormatWriterPresenter";

    public FormatWriterPresenter(FormatWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "FormatWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "FormatWriterPresenter: Users Repository can't be null.");
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
