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
import com.easynfc.mytags.MyTagsContract;
import com.easynfc.writer.app_launcher.AppLauncherWriterContract;
import com.easynfc.writer.email.EmailWriterContract;
import com.easynfc.writer.location.LocationWriterContract;
import com.easynfc.writer.phone.PhoneWriterContract;
import com.easynfc.writer.simple_text.SimpleTextWriterContract;
import com.easynfc.writer.sms.SmsWriterContract;
import com.easynfc.writer.url.UrlWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterContract;

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
    public void addTextTag(TextTag textTag, final OnTagSavedCallback callback) {
        remoteDataSource.addTextTag(textTag, new OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void addUrl(UrlTag urlTag, final OnTagSavedCallback callback) {
        remoteDataSource.addUrl(urlTag, new OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void addSms(SmsTag smsTag, final OnTagSavedCallback callback) {
        remoteDataSource.addSms(smsTag, new OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void addPhone(PhoneTag phoneTag, final OnTagSavedCallback callback) {
        remoteDataSource.addPhone(phoneTag, new OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void addAar(AarTag aarTag, final OnTagSavedCallback callback) {
        remoteDataSource.addAar(aarTag, new OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
              callback.onError();
            }
        });
    }

    @Override
    public void addLocation(LocationTag locationTag, final OnTagSavedCallback callback) {
        remoteDataSource.addLocation(locationTag, new OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void addWifi(WifiTag wifiTag, final OnTagSavedCallback callback) {
        remoteDataSource.addWifi(wifiTag, new OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void addEmail(EmailTag emailTag, final OnTagSavedCallback callback) {
        remoteDataSource.addEmail(emailTag, new OnTagSavedCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void deleteTag(MyTag myTag, MyTagsContract.OnDeleteTagCallback callback) {
        remoteDataSource.deleteTag(myTag, callback);
    }

    @Override
    public void getTextTag(long timestamp, final SimpleTextWriterContract.LoadTextTagCallback callback) {
        remoteDataSource.getTextTag(timestamp, new SimpleTextWriterContract.LoadTextTagCallback() {
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

    @Override
    public void getAarTag(long tagId, final AppLauncherWriterContract.LoadAarTagCallback callback) {
        remoteDataSource.getAarTag(tagId, new AppLauncherWriterContract.LoadAarTagCallback() {
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
    public void getEmailTag(long tagId, final EmailWriterContract.LoadEmailTagCallback callback) {
        remoteDataSource.getEmailTag(tagId, new EmailWriterContract.LoadEmailTagCallback() {
            @Override
            public void onTagLoaded(EmailTag emailTag) {
                callback.onTagLoaded(emailTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }

    @Override
    public void getLocationTag(long tagId, final LocationWriterContract.LoadLocationTagCallback callback) {
        remoteDataSource.getLocationTag(tagId, new LocationWriterContract.LoadLocationTagCallback() {
            @Override
            public void onTagLoaded(LocationTag locationTag) {
                callback.onTagLoaded(locationTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }

    @Override
    public void getPhoneTag(long tagId, final PhoneWriterContract.LoadPhoneTagCallback callback) {
        remoteDataSource.getPhoneTag(tagId, new PhoneWriterContract.LoadPhoneTagCallback() {
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

    @Override
    public void getSmsTag(long tagId, final SmsWriterContract.LoadSmsTagCallback callback) {
        remoteDataSource.getSmsTag(tagId, new SmsWriterContract.LoadSmsTagCallback() {
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
    public void getUrlTag(long tagId, final UrlWriterContract.LoadUrlTagCallback callback) {
        remoteDataSource.getUrlTag(tagId, new UrlWriterContract.LoadUrlTagCallback() {
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
    public void getWifiTag(long tagId, final WiFiWriterContract.LoadWifiTagCallback callback) {
        remoteDataSource.getWifiTag(tagId, new WiFiWriterContract.LoadWifiTagCallback() {
            @Override
            public void onTagLoaded(WifiTag wifiTag) {
                callback.onTagLoaded(wifiTag);
            }

            @Override
            public void onDatanotAvailable() {
                callback.onDatanotAvailable();
            }
        });
    }

    @Override
    public void updateTag(TextTag textTag, OnTagUpdatedCallback callback) {

    }

}
