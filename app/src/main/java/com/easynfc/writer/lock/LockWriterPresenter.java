package com.easynfc.writer.lock;

import android.content.Intent;
import android.util.Log;

import com.easynfc.util.NfcUtils;
import com.easynfc.writer.phone.PhoneWriterContract;

/**
 * Created by pablorojas on 28/2/18.
 */

public class LockWriterPresenter  implements LockWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private LockWriterContract.View view;
    private static String TAG = "LockWriterPresenter";

    public LockWriterPresenter(LockWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "LockWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "LockWriterPresenter: Users Repository can't be null.");
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
