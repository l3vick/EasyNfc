package com.easynfc.writer.sms;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.util.Log;

import com.easynfc.data.SmsTag;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.TagsRepository;
import com.easynfc.util.NfcUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;

/**
 * Created by pablorojas on 28/2/18.
 */

public class SmsWriterPresenter implements SmsWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private SmsWriterContract.View view;
    private TagsRepository tagsRepository;
    private static String TAG = "SmsWriterPresenter";

    public SmsWriterPresenter(SmsWriterContract.View view, NfcUtils nfcUtils, TagsRepository tagsRepository) {
        if (nfcUtils != null && tagsRepository != null) {
            this.nfcUtils = nfcUtils;
            this.tagsRepository = tagsRepository;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "View can't be null.");
            }
        } else {
            Log.d(TAG, "NfcUtils & Tags Repository can't be null.");
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

    @Override
    public void saveTag(String number, String text) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tagsRepository.addSms(new SmsTag(timestamp.getTime(), number, text), new TagsDataSource.OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                view.showMessageSuccess();
            }

            @Override
            public void onError() {
                view.showMessageError();
            }
        });
    }

    @Override
    public void loadTag(long tagId, final SmsWriterContract.LoadSmsTagCallback callback) {
        tagsRepository.getSmsTag(tagId, new SmsWriterContract.LoadSmsTagCallback() {
            @Override
            public void onTagLoaded(SmsTag smsTag) {
                callback.onTagLoaded(smsTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }

    @Override
    public void emulateTag(String number, String text) {
        nfcUtils.emulateSmsTag(number, text);
    }
}
