package com.easynfc.writer.url;

import android.content.Intent;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.UrlTag;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.TagsRepository;
import com.easynfc.util.NfcUtils;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by pablorojas on 26/2/18.
 */

public class UrlWriterPresenter implements UrlWriterContract.Presenter {

    private static final String TAG = "UrlWriterPresenter";
    private UrlWriterContract.View view;
    private TagsRepository tagsRepository;
    private NfcUtils nfcUtils;


    public UrlWriterPresenter(UrlWriterContract.View view, NfcUtils nfcUtils, TagsRepository tagsRepository) {
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
    public void writeTag(Intent intent, String text) {
        try {
            nfcUtils.writeUrlTag(intent, text, new NfcUtils.TagWrittenCallback() {
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
    public void saveTag(String content) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tagsRepository.addUrl(new UrlTag(timestamp.getTime(), content), new TagsDataSource.OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                view.showAddedSuccess();
            }

            @Override
            public void onError() {
                view.showMessageError();
            }
        });
    }

    @Override
    public void loadTag(long tagId, final UrlWriterContract.LoadUrlTagCallback callback) {
        tagsRepository.getUrlTag(tagId, new UrlWriterContract.LoadUrlTagCallback() {
            @Override
            public void onTagLoaded(UrlTag urlTag) {
                callback.onTagLoaded(urlTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }

    @Override
    public void emulateTag(String url) {
        nfcUtils.emulateUrlTag(url);
    }
}
