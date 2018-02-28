package com.easynfc.writer.email;

import android.content.Intent;
import android.util.Log;

import com.easynfc.util.NfcUtils;
import com.easynfc.writer.location.LocationWriterContract;

/**
 * Created by pablorojas on 28/2/18.
 */

public class EmailWriterPresenter implements EmailWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private EmailWriterContract.View view;
    private static String TAG = "EmailWriterPresenter";

    public EmailWriterPresenter(EmailWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "EmailWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "EmailWriterPresenter: Users Repository can't be null.");
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
