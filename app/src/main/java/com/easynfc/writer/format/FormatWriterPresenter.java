package com.easynfc.writer.format;

import android.content.Intent;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.util.NfcUtils;

import java.io.IOException;

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
    public void formatTag(Intent intent) {
        try {
            nfcUtils.formatEmptyTag(intent, new NfcUtils.TagWrittenCallback() {
                @Override
                public void OnSuccess() {
                    view.onTagWritten();
                }

                @Override
                public void OnError() {

                }
            });
        } catch (ReadOnlyTagException e) {
            e.printStackTrace();
        } catch (NdefFormatException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (InsufficientSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enableForegroundDispatch() {
        nfcUtils.enableForegroundDispatch();
    }


    @Override
    public void disableForegroundDispatch() {
        nfcUtils.disableForegroundDispatch();
    }

}
