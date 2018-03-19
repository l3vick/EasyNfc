package com.easynfc.writer.app_launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.nfc.FormatException;
import android.util.Log;

import com.easynfc.data.AarTag;
import com.easynfc.data.TextTag;
import com.easynfc.data.exceptions.InsufficientSizeException;
import com.easynfc.data.exceptions.NdefFormatException;
import com.easynfc.data.exceptions.ReadOnlyTagException;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.TagsRepository;
import com.easynfc.util.NfcUtils;
import com.easynfc.writer.simple_text.SimpleTextWriterContract;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablorojas on 28/2/18.
 */

public class AppLauncherWriterPresenter implements AppLauncherWriterContract.Presenter {

    private NfcUtils nfcUtils;
    private AppLauncherWriterContract.View view;
    private TagsRepository tagsRepository;
    private static String TAG = "AppLauncherWriterPresenter";

    public AppLauncherWriterPresenter(AppLauncherWriterContract.View view, NfcUtils nfcUtils, TagsRepository tagsRepository) {
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

    @Override
    public List<String> getInstalledPackageNameList(Activity activity) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = activity.getPackageManager().queryIntentActivities(mainIntent, 0);

        List<String> list = new ArrayList<>();
        for (ResolveInfo item : pkgAppsList) {

            list.add(item.activityInfo.packageName);
            String currentHomePackage = item.activityInfo.packageName;
            Log.v("app", "" + currentHomePackage);
        }

        return list;
    }

    @Override
    public void saveTag(String aar) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tagsRepository.addAar(new AarTag(timestamp.getTime(), aar), new TagsDataSource.OnTagSavedCallback() {
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
    public void loadTag(long tagId, final AppLauncherWriterContract.LoadAarTagCallback callback) {
        tagsRepository.getAarTag(tagId, new AppLauncherWriterContract.LoadAarTagCallback() {
            @Override
            public void onTagLoaded(AarTag aarTag) {
                callback.onTagLoaded(aarTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }

    @Override
    public void emulateTag(String aar) {
        nfcUtils.emulateAarTag(aar);
    }
}
