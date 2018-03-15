package com.easynfc.writer.simple_text;

import android.content.Intent;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.TextTag;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.source.TagsRepository;
import com.easynfc.util.NfcUtils;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by pablorojas on 26/2/18.
 */

public class SimpleTextWriterPresenter implements SimpleTextWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private SimpleTextWriterContract.View view;
    private TagsRepository tagsRepository;
    private static String TAG = "SimpleTextWriterPresenter";

    public SimpleTextWriterPresenter(SimpleTextWriterContract.View view, NfcUtils nfcUtils, TagsRepository tagsRepository) {

        if (nfcUtils != null && tagsRepository != null) {
            this.nfcUtils = nfcUtils;
            this.tagsRepository = tagsRepository;
            if (view != null) {
                this.view = view;
                view.setPresenter(this);
            } else {
                Log.d(TAG, "SimpleTextWriterPresenter: View can't be null.");
            }
        } else {
            Log.d(TAG, "SimpleTextWriterPresenter: NfcUtils && Tags Repository can't be null.");
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
    public void saveTag(String content) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tagsRepository.addText(new TextTag(timestamp.getTime(),content));
    }

    @Override
    public void loadTag(long timestamp, final SimpleTextWriterContract.LoadTextTagCallback callback) {
        tagsRepository.getTextTag(timestamp, new SimpleTextWriterContract.LoadTextTagCallback() {
            @Override
            public void onTagLoaded(TextTag textTag) {
                callback.onTagLoaded(textTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }

}
