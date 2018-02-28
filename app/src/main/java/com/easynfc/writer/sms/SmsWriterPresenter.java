package com.easynfc.writer.sms;

import android.content.Intent;
import android.util.Log;

import com.easynfc.util.NfcUtils;
import com.easynfc.writer.simple_text.SimpleTextWriterContract;

/**
 * Created by pablorojas on 28/2/18.
 */

public class SmsWriterPresenter implements SmsWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private SmsWriterContract.View view;
    private static String TAG = "SmsWriterPresenter";

    public SmsWriterPresenter(SmsWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "SmsWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "SmsWriterPresenter: Users Repository can't be null.");
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
