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
import com.easynfc.data.model.Wifi;
import com.easynfc.data.source.TagsDataSource;
import com.easynfc.data.source.local.EasyNfcDatabase;
import com.easynfc.mytags.MyTagsContract;
import com.easynfc.util.AppConstants;
import com.easynfc.writer.app_launcher.AppLauncherWriterContract;
import com.easynfc.writer.email.EmailWriterContract;
import com.easynfc.writer.location.LocationWriterContract;
import com.easynfc.writer.phone.PhoneWriterContract;
import com.easynfc.writer.simple_text.SimpleTextWriterContract;
import com.easynfc.writer.sms.SmsWriterContract;
import com.easynfc.writer.url.UrlWriterContract;
import com.easynfc.writer.wi_fi.WiFiWriterContract;

import java.net.URL;
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
        tagsDao.insertTag(new MyTag(textTag.getTimeStamp(), textTag.getContent(), AppConstants.TagTypes.TEXT.toString()));
        tagsDao.insertTextTag(textTag);
    }

    @Override
    public void addUrl(UrlTag urlTag) {
        tagsDao.insertTag(new MyTag(urlTag.getTimeStamp(), urlTag.getContent(), AppConstants.TagTypes.URL.toString()));
        tagsDao.insertUrlTag(urlTag);
    }

    @Override
    public void addSms(SmsTag smsTag) {
        tagsDao.insertTag(new MyTag(smsTag.getTimeStamp(), smsTag.getText(), AppConstants.TagTypes.SMS.toString()));
        tagsDao.insertSmsTag(smsTag);
    }

    @Override
    public void addPhone(PhoneTag phoneTag) {
        tagsDao.insertTag(new MyTag(phoneTag.getTimeStamp(), phoneTag.getPhone(), AppConstants.TagTypes.PHONE.toString()));
        tagsDao.insertPhoneTag(phoneTag);
    }

    @Override
    public void addAar(AarTag aarTag) {
        tagsDao.insertTag(new MyTag(aarTag.getTimeStamp(), aarTag.getAar(), AppConstants.TagTypes.AAR.toString()));
        tagsDao.insertAarTag(aarTag);
    }

    @Override
    public void addLocation(LocationTag locationTag) {
        tagsDao.insertTag(new MyTag(locationTag.getTimeStamp(), locationTag.getLatitude() + ", " + locationTag.getLongitude(), AppConstants.TagTypes.LOCATION.toString()));
        tagsDao.insertLocationTag(locationTag);
    }

    @Override
    public void addWifi(WifiTag wifiTag) {
        tagsDao.insertTag(new MyTag(wifiTag.getTimeStamp(), wifiTag.getSsid(), AppConstants.TagTypes.WIFI.toString()));
        tagsDao.insertWifiTag(wifiTag);
    }

    @Override
    public void addEmail(EmailTag emailTag) {
        tagsDao.insertTag(new MyTag(emailTag.getTimeStamp(), emailTag.getContent(), AppConstants.TagTypes.EMAIL.toString()));
        tagsDao.insertEmailTag(emailTag);
    }

    @Override
    public void deleteTag(MyTag myTag,  MyTagsContract.OnDeleteTagCallback callback) {
        tagsDao.deleteTag(myTag);
        switch (AppConstants.getTypeTag(myTag.getType())) {
            case TEXT:
                tagsDao.deleteTextTag(myTag.getTimestamp());
                break;
            case URL:
                tagsDao.deleteUrlTag(myTag.getTimestamp());
                break;
            case SMS:
                tagsDao.deleteSmsTag(myTag.getTimestamp());
                break;
            case PHONE:
                tagsDao.deletePhoneTag(myTag.getTimestamp());
                break;
            case AAR:
                tagsDao.deleteAarTag(myTag.getTimestamp());
                break;
            case LOCATION:
                tagsDao.deleteLocationTag(myTag.getTimestamp());
                break;
            case WIFI:
                tagsDao.deleteWifiTag(myTag.getTimestamp());
                break;
            case EMAIL:
                tagsDao.deleteEmailTag(myTag.getTimestamp());
                break;
            default:
                break;
        }
        callback.OnSuccess();
    }

    @Override
    public void getTextTag(long timestamp, SimpleTextWriterContract.LoadTextTagCallback callback) {
        TextTag textTag = tagsDao.getTextTag(timestamp);
        if (textTag != null){
            callback.onTagLoaded(textTag);
        }else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getAarTag(long tagId, AppLauncherWriterContract.LoadAarTagCallback callback) {
        AarTag aarTag = tagsDao.getAarTag(tagId);
        if (aarTag != null){
            callback.onTagLoaded(aarTag);
        }else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getEmailTag(long tagId, EmailWriterContract.LoadEmailTagCallback callback) {
       EmailTag emailTag = tagsDao.getEmailTag(tagId);
       if (emailTag != null){
           callback.onTagLoaded(emailTag);
       }else {
           callback.onDatanotAvailable();
       }
    }

    @Override
    public void getLocationTag(long tagId, LocationWriterContract.LoadLocationTagCallback callback) {
        LocationTag locationTag = tagsDao.getLocationTag(tagId);
        if (locationTag != null){
            callback.onTagLoaded(locationTag);
        }else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getPhoneTag(long tagId, PhoneWriterContract.LoadPhoneTagCallback callback) {
        PhoneTag phoneTag = tagsDao.getPhoneTag(tagId);
        if (phoneTag != null){
            callback.onTagLoaded(phoneTag);
        }else{
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getSmsTag(long tagId, SmsWriterContract.LoadSmsTagCallback callback) {
        SmsTag smsTag = tagsDao.getSmsTag(tagId);
        if (smsTag != null){
            callback.onTagLoaded(smsTag);
        }else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getUrlTag(long tagId, UrlWriterContract.LoadUrlTagCallback callback) {
        UrlTag urlTag= tagsDao.getUrlTag(tagId);
        if (urlTag != null){
            callback.onTagLoaded(urlTag);
        }else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getWifiTag(long tagId, WiFiWriterContract.LoadWifiTagCallback callback) {
        WifiTag wifiTag= tagsDao.getWifiTag(tagId);
        if (wifiTag != null){
            callback.onTagLoaded(wifiTag);
        }else {
            callback.onDatanotAvailable();
        }
    }
}
