package com.easynfc.writer.sms;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.util.Log;

import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.util.NfcUtils;

import java.io.IOException;
import java.net.URLEncoder;

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
        this.view = null;
    }

    @Override
    public void enableForegroundDispatch() {
        nfcUtils.enableForegroundDispatch();
    }

    @Override
    public void writeTag(Intent intent, String number, String text) {
        try {
            nfcUtils.writeSmsTag(intent, number, text, new NfcUtils.TagWrittenCallback() {
                @Override
                public void OnSuccess() {
                    view.OnTagWritten();
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
    public void disableForegroundDispatch() {
        nfcUtils.disableForegroundDispatch();
    }
}
