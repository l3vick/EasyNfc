package com.easynfc.writer.location;

import android.content.Intent;
import android.util.Log;

import com.easynfc.util.NfcUtils;
import com.easynfc.writer.lock.LockWriterContract;

/**
 * Created by pablorojas on 28/2/18.
 */

public class LocationWriterPresenter implements LocationWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private LocationWriterContract.View view;
    private static String TAG = "LocationWriterPresenter";

    public LocationWriterPresenter(LocationWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "LocationWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "LocationWriterPresenter: Users Repository can't be null.");
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
