package com.easynfc.writer.SimpleText;

import android.content.Intent;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.util.NfcUtils;
import com.easynfc.writer.WriterActivity;

import java.io.IOException;

/**
 * Created by pablorojas on 26/2/18.
 */

public class SimpleTextWriterPresenter implements SimpleTextWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private SimpleTextWriterContract.View view;
    private static String TAG = "SimpleTextWriterPresenter";

    public SimpleTextWriterPresenter(SimpleTextWriterContract.View view, NfcUtils nfcUtils) {
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
        view = null;
    }

    @Override
    public void writeTag(Intent intent, String text) {
        if (nfcUtils.isNfcIntent(intent)) {
            try {
                nfcUtils.writeSimpleTextTag(intent, text, new NfcUtils.TagWrittenCallback() {
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
