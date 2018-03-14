package com.easynfc.data.source.local.tags;

import android.content.Context;

import com.easynfc.data.AarTag;
import com.easynfc.data.EmailTag;
import com.easynfc.data.LocationTag;
import com.easynfc.data.PhoneTag;
import com.easynfc.data.SmsTag;
import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;
import com.easynfc.data.MyTag;
import com.easynfc.data.WifiTag;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.local.EasyNfcDatabase;
import com.easynfc.mytags.MyTagsContract;
import com.easynfc.util.AppConstants;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

public class TagsLocalDataSource implements TagsDataSource {

    private static final String TAG = "MenuLocalDataSource";

    private static TagsLocalDataSource INSTANCE;
    private static TagsDao tagsDao;

    private TagsLocalDataSource() {

    }

    public static TagsLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TagsLocalDataSource();
            tagsDao = EasyNfcDatabase.getAppDatabase(context).tagsDao();
        }
        return INSTANCE;
    }

    @Override
    public void getTags(LoadTagsCallback callback) {
        final List<MyTag> tags = tagsDao.getTags();
        if (tags.size() > 0) {
            callback.onTagsLoaded(tags);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void addText(TextTag textTag) {
        tagsDao.insertTag(new MyTag(textTag.getTimeStamp(), textTag.getContent(), AppConstants.TEXT));
        tagsDao.insertTextTag(textTag);
    }

    @Override
    public void addUrl(UrlTag urlTag) {
        tagsDao.insertTag(new MyTag(urlTag.getTimeStamp(), urlTag.getContent(), AppConstants.URL));
        tagsDao.insertUrlTag(urlTag);
    }

    @Override
    public void addSms(SmsTag smsTag) {
        tagsDao.insertTag(new MyTag(smsTag.getTimeStamp(), smsTag.getText(), AppConstants.SMS));
        tagsDao.insertSmsTag(smsTag);
    }

    @Override
    public void addPhone(PhoneTag phoneTag) {
        tagsDao.insertTag(new MyTag(phoneTag.getTimeStamp(), phoneTag.getPhone(), AppConstants.PHONE));
        tagsDao.insertPhoneTag(phoneTag);
    }

    @Override
    public void addAar(AarTag aarTag) {
        tagsDao.insertTag(new MyTag(aarTag.getTimeStamp(), aarTag.getAar(), AppConstants.AAR));
        tagsDao.insertAarTag(aarTag);
    }

    @Override
    public void addLocation(LocationTag locationTag) {
        tagsDao.insertTag(new MyTag(locationTag.getTimeStamp(), locationTag.getLatitude() + ", " + locationTag.getLongitude(), AppConstants.LOCATION));
        tagsDao.insertLocationTag(locationTag);
    }

    @Override
    public void addWifi(WifiTag wifiTag) {
        tagsDao.insertTag(new MyTag(wifiTag.getTimeStamp(), wifiTag.getSsid(), AppConstants.WIFI));
        tagsDao.insertWifiTag(wifiTag);
    }

    @Override
    public void addEmail(EmailTag emailTag) {
        tagsDao.insertTag(new MyTag(emailTag.getTimeStamp(), emailTag.getContent(), AppConstants.EMAIL));
        tagsDao.insertEmailTag(emailTag);
    }

    @Override
    public void deleteTag(MyTag myTag,  MyTagsContract.OnDeleteTagCallback callback) {
        tagsDao.deleteTag(myTag);
        switch (myTag.getType()) {
            case AppConstants.TEXT:
                tagsDao.deleteTextTag(myTag.getTimestamp());
                break;
            case AppConstants.URL:
                tagsDao.deleteUrlTag(myTag.getTimestamp());
                break;
            case AppConstants.SMS:
                tagsDao.deleteSmsTag(myTag.getTimestamp());
                break;
            case AppConstants.PHONE:
                tagsDao.deletePhoneTag(myTag.getTimestamp());
                break;
            case AppConstants.AAR:
                tagsDao.deleteAarTag(myTag.getTimestamp());
                break;
            case AppConstants.LOCATION:
                tagsDao.deleteLocationTag(myTag.getTimestamp());
                break;
            case AppConstants.WIFI:
                tagsDao.deleteWifiTag(myTag.getTimestamp());
                break;
            case AppConstants.EMAIL:
                tagsDao.deleteEmailTag(myTag.getTimestamp());
                break;
            default:
                break;
        }
        callback.OnSuccess();
    }
}
