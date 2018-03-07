package com.easynfc.reader;

import android.content.Intent;
import android.util.Log;

import com.easynfc.data.model.TagData;
import com.easynfc.util.NfcUtils;

/**
 * Created by pablorojas on 7/3/18.
 */

public class ReaderPresenter implements ReaderContract.Presenter {

    private ReaderContract.View view;
    private NfcUtils nfcUtils;

    private static String TAG  = "ReaderPresenter";

    public ReaderPresenter(ReaderContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "ReaderPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "ReaderPresenter: NfcUtils can't be null.");
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        this.view = null;
    }

    @Override
    public void enableForegroundDispatch() {
        nfcUtils.enableForegroundDispatch();
    }

    @Override
    public void disableForegroundDispatch() {
        nfcUtils.disableForegroundDispatch();
    }

    @Override
    public void processNfc(Intent intent) {
        nfcUtils.handleIntent(intent, new NfcUtils.TagReadedCallback() {
            @Override
            public void OnSuccess(TagData tagData) {
                view.setTagData(tagData);
            }
        });
    }
}
