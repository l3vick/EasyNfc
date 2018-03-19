package com.easynfc.writer.phone;

import android.content.Intent;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.PhoneTag;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.TagsRepository;
import com.easynfc.util.NfcUtils;
import com.easynfc.writer.wi_fi.WiFiWriterContract;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by pablorojas on 28/2/18.
 */

public class PhoneWriterPresenter  implements PhoneWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private PhoneWriterContract.View view;
    private TagsRepository tagsRepository;
    private static String TAG = "SmsWriterPresenter";

    public PhoneWriterPresenter(PhoneWriterContract.View view, NfcUtils nfcUtils, TagsRepository tagsRepository) {
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

    }

    @Override
    public void enableForegroundDispatch() {
        nfcUtils.enableForegroundDispatch();
    }

    @Override
    public void writeTag(Intent intent, String number) {
        try {
            nfcUtils.writePhoneTag(intent, number, new NfcUtils.TagWrittenCallback() {
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
    public void saveTag(String phone) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tagsRepository.addPhone(new PhoneTag(timestamp.getTime(), phone), new TagsDataSource.OnTagSavedCallback() {
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
    public void loadTag(long tagId, final PhoneWriterContract.LoadPhoneTagCallback callback) {
        tagsRepository.getPhoneTag(tagId, new PhoneWriterContract.LoadPhoneTagCallback() {
            @Override
            public void onTagLoaded(PhoneTag phoneTag) {
                callback.onTagLoaded(phoneTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }
}
