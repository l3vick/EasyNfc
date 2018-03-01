package com.easynfc.writer.app_launcher;

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

public class AppLauncherWriterPresenter implements AppLauncherWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private AppLauncherWriterContract.View view;
    private static String TAG = "AppLauncherWriterPresenter";

    public AppLauncherWriterPresenter(AppLauncherWriterContract.View view, NfcUtils nfcUtils) {
        if (nfcUtils != null) {
            this.nfcUtils = nfcUtils;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "AppLauncherWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "AppLauncherWriterPresenter: Users Repository can't be null.");
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
    public void writeTag(Intent intent, String text) {
        try {
            nfcUtils.writeAppLauncherTag(intent, text, new NfcUtils.TagWrittenCallback() {
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
