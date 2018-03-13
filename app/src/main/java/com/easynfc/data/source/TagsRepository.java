package com.easynfc.data.source;

import com.easynfc.data.AarTag;
import com.easynfc.data.EmailTag;
import com.easynfc.data.LocationTag;
import com.easynfc.data.PhoneTag;
import com.easynfc.data.SmsTag;
import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;
import com.easynfc.data.MyTag;
import com.easynfc.data.WifiTag;
import com.easynfc.data.source.local.tags.TagsLocalDataSource;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

public class TagsRepository implements TagsDataSource {

    private static final String TAG = "TagsRepository";
    private static TagsRepository INSTANCE = null;
    private TagsDataSource remoteDataSource;

    public TagsRepository(TagsLocalDataSource remoteDataSource) {
        if (remoteDataSource != null) {
            this.remoteDataSource = remoteDataSource;
        }
    }

    public static TagsRepository getInstance(TagsLocalDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TagsRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getTags(final LoadTagsCallback callback) {
        remoteDataSource.getTags(new LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<MyTag> tags) {
                callback.onTagsLoaded(tags);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addText(TextTag textTag) {
        remoteDataSource.addText(textTag);
    }

    @Override
    public void addUrl(UrlTag urlTag) {
        remoteDataSource.addUrl(urlTag);
    }

    @Override
    public void addSms(SmsTag smsTag) {
        remoteDataSource.addSms(smsTag);
    }

    @Override
    public void addPhone(PhoneTag phoneTag) {
        remoteDataSource.addPhone(phoneTag);
    }

    @Override
    public void addAar(AarTag aarTag) {
        remoteDataSource.addAar(aarTag);
    }

    @Override
    public void addLocation(LocationTag locationTag) {
        remoteDataSource.addLocation(locationTag);
    }

    @Override
    public void addWifi(WifiTag wifiTag) {
        remoteDataSource.addWifi(wifiTag);
    }

    @Override
    public void addEmail(EmailTag emailTag) {
        remoteDataSource.addEmail(emailTag);
    }

}
