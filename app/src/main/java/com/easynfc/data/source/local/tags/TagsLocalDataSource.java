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
    public void addTextTag(TextTag textTag, OnTagSavedCallback callback) {
        tagsDao.insertTag(new MyTag(textTag.getTimeStamp(), textTag.getContent(), AppConstants.TagTypes.TEXT.toString()));
        tagsDao.insertTextTag(textTag);
        TextTag textTagAux = tagsDao.getTextTag(textTag.getTimeStamp());
        if (textTagAux != null) {
            callback.onSuccess();
        } else {
            callback.onError();
        }
    }

    @Override
    public void addUrl(UrlTag urlTag, OnTagSavedCallback callback) {
        tagsDao.insertTag(new MyTag(urlTag.getTimeStamp(), urlTag.getContent(), AppConstants.TagTypes.URL.toString()));
        tagsDao.insertUrlTag(urlTag);
        UrlTag urlTagAux = tagsDao.getUrlTag(urlTag.getTimeStamp());
        if (urlTagAux != null) {
            callback.onSuccess();
        } else {
            callback.onError();
        }
    }

    @Override
    public void addSms(SmsTag smsTag, OnTagSavedCallback callback) {
        tagsDao.insertTag(new MyTag(smsTag.getTimeStamp(), smsTag.getText(), AppConstants.TagTypes.SMS.toString()));
        tagsDao.insertSmsTag(smsTag);
        SmsTag smsTagAux = tagsDao.getSmsTag(smsTag.getTimeStamp());
        if (smsTagAux != null) {
            callback.onSuccess();
        } else {
            callback.onError();
        }
    }

    @Override
    public void addPhone(PhoneTag phoneTag, OnTagSavedCallback callback) {
        tagsDao.insertTag(new MyTag(phoneTag.getTimeStamp(), phoneTag.getPhone(), AppConstants.TagTypes.PHONE.toString()));
        tagsDao.insertPhoneTag(phoneTag);
        PhoneTag phoneTagAux = tagsDao.getPhoneTag(phoneTag.getTimeStamp());
        if (phoneTagAux != null) {
            callback.onSuccess();
        } else {
            callback.onError();
        }
    }

    @Override
    public void addAar(AarTag aarTag, OnTagSavedCallback callback) {
        tagsDao.insertTag(new MyTag(aarTag.getTimeStamp(), aarTag.getAar(), AppConstants.TagTypes.AAR.toString()));
        tagsDao.insertAarTag(aarTag);
        AarTag aarTagAux = tagsDao.getAarTag(aarTag.getTimeStamp());
        if (aarTagAux != null) {
            callback.onSuccess();
        } else {
            callback.onError();
        }
    }

    @Override
    public void addLocation(LocationTag locationTag, OnTagSavedCallback callback) {
        tagsDao.insertTag(new MyTag(locationTag.getTimeStamp(), locationTag.getLatitude() + ", " + locationTag.getLongitude(), AppConstants.TagTypes.LOCATION.toString()));
        tagsDao.insertLocationTag(locationTag);
        LocationTag locationTagAux = tagsDao.getLocationTag(locationTag.getTimeStamp());
        if (locationTagAux != null) {
            callback.onSuccess();
        } else {
            callback.onError();
        }
    }

    @Override
    public void addWifi(WifiTag wifiTag, OnTagSavedCallback callback) {
        tagsDao.insertTag(new MyTag(wifiTag.getTimeStamp(), wifiTag.getSsid(), AppConstants.TagTypes.WIFI.toString()));
        tagsDao.insertWifiTag(wifiTag);
        WifiTag wifiTagAux = tagsDao.getWifiTag(wifiTag.getTimeStamp());
        if (wifiTagAux != null) {
            callback.onSuccess();
        } else {
            callback.onError();
        }
    }

    @Override
    public void addEmail(EmailTag emailTag, OnTagSavedCallback callback) {
        tagsDao.insertTag(new MyTag(emailTag.getTimeStamp(), emailTag.getContent(), AppConstants.TagTypes.EMAIL.toString()));
        tagsDao.insertEmailTag(emailTag);
        EmailTag emailTagAux = tagsDao.getEmailTag(emailTag.getTimeStamp());
        if (emailTagAux != null) {
            callback.onSuccess();
        } else {
            callback.onError();
        }
    }

    @Override
    public void deleteTag(MyTag myTag, MyTagsContract.OnDeleteTagCallback callback) {
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
        if (textTag != null) {
            callback.onTagLoaded(textTag);
        } else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getAarTag(long tagId, AppLauncherWriterContract.LoadAarTagCallback callback) {
        AarTag aarTag = tagsDao.getAarTag(tagId);
        if (aarTag != null) {
            callback.onTagLoaded(aarTag);
        } else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getEmailTag(long tagId, EmailWriterContract.LoadEmailTagCallback callback) {
        EmailTag emailTag = tagsDao.getEmailTag(tagId);
        if (emailTag != null) {
            callback.onTagLoaded(emailTag);
        } else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getLocationTag(long tagId, LocationWriterContract.LoadLocationTagCallback callback) {
        LocationTag locationTag = tagsDao.getLocationTag(tagId);
        if (locationTag != null) {
            callback.onTagLoaded(locationTag);
        } else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getPhoneTag(long tagId, PhoneWriterContract.LoadPhoneTagCallback callback) {
        PhoneTag phoneTag = tagsDao.getPhoneTag(tagId);
        if (phoneTag != null) {
            callback.onTagLoaded(phoneTag);
        } else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getSmsTag(long tagId, SmsWriterContract.LoadSmsTagCallback callback) {
        SmsTag smsTag = tagsDao.getSmsTag(tagId);
        if (smsTag != null) {
            callback.onTagLoaded(smsTag);
        } else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getUrlTag(long tagId, UrlWriterContract.LoadUrlTagCallback callback) {
        UrlTag urlTag = tagsDao.getUrlTag(tagId);
        if (urlTag != null) {
            callback.onTagLoaded(urlTag);
        } else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void getWifiTag(long tagId, WiFiWriterContract.LoadWifiTagCallback callback) {
        WifiTag wifiTag = tagsDao.getWifiTag(tagId);
        if (wifiTag != null) {
            callback.onTagLoaded(wifiTag);
        } else {
            callback.onDatanotAvailable();
        }
    }

    @Override
    public void updateTextTag(final TextTag textTag, OnTagUpdatedCallback callback) {
        tagsDao.updateTextTag(textTag);
        tagsDao.updateTag(new MyTag(textTag.getTimeStamp(), textTag.getContent(),AppConstants.TagTypes.TEXT.toString()));
        final TextTag auxTextTag = tagsDao.getTextTag(textTag.getTimeStamp());

        if (auxTextTag.getContent().equals(textTag.getContent())  && auxTextTag.getTimeStamp() == textTag.getTimeStamp()){
            callback.onSuccess();
        }else{
            callback.onError();
        }
    }

    @Override
    public void updateAarTag(AarTag aarTag, OnTagUpdatedCallback callback) {
        tagsDao.updateAarTag(aarTag);
        tagsDao.updateTag(new MyTag(aarTag.getTimeStamp(), aarTag.getAar(),AppConstants.TagTypes.AAR.toString()));

        final AarTag auxAarTag = tagsDao.getAarTag(aarTag.getTimeStamp());
        if (auxAarTag.getAar().equals(aarTag.getAar())  && auxAarTag.getTimeStamp() == aarTag.getTimeStamp()){
            callback.onSuccess();
        }else{
            callback.onError();
        }
    }

    @Override
    public void updateEmailTag(EmailTag emailTag, OnTagUpdatedCallback callback) {
        tagsDao.updateEmailTag(emailTag);
        tagsDao.updateTag(new MyTag(emailTag.getTimeStamp(), emailTag.getContent(),AppConstants.TagTypes.EMAIL.toString()));

        final EmailTag auxEmailTag = tagsDao.getEmailTag(emailTag.getTimeStamp());
        if (auxEmailTag.getContent().equals(emailTag.getContent())  && auxEmailTag.getTimeStamp() == emailTag.getTimeStamp()){
            callback.onSuccess();
        }else{
            callback.onError();
        }
    }

    @Override
    public void updateLocationTag(LocationTag locationTag, OnTagUpdatedCallback callback) {
        tagsDao.updateLocationTag(locationTag);
        tagsDao.updateTag(new MyTag(locationTag.getTimeStamp(), locationTag.getLatitude() + ", " +locationTag.getLatitude() ,AppConstants.TagTypes.LOCATION.toString()));

        final LocationTag auxLocationTag = tagsDao.getLocationTag(locationTag.getTimeStamp());
        if (auxLocationTag.getLatitude().equals(locationTag.getLatitude())  && auxLocationTag.getTimeStamp() == locationTag.getTimeStamp()){
            callback.onSuccess();
        }else{
            callback.onError();
        }
    }

    @Override
    public void updatePhoneTag(PhoneTag phoneTag, OnTagUpdatedCallback callback) {
        tagsDao.updatePhoneTag(phoneTag);
        tagsDao.updateTag(new MyTag(phoneTag.getTimeStamp(), phoneTag.getPhone(),AppConstants.TagTypes.PHONE.toString()));

        final PhoneTag auxPhoneTag = tagsDao.getPhoneTag(phoneTag.getTimeStamp());
        if (auxPhoneTag.getPhone().equals(phoneTag.getPhone())  && auxPhoneTag.getTimeStamp() == phoneTag.getTimeStamp()){
            callback.onSuccess();
        }else{
            callback.onError();
        }
    }

    @Override
    public void updateSmsTag(SmsTag smsTag, OnTagUpdatedCallback callback) {
        tagsDao.updateSmsTag(smsTag);
        tagsDao.updateTag(new MyTag(smsTag.getTimeStamp(), smsTag.getNumber(), AppConstants.TagTypes.SMS.toString()));

        final SmsTag auxSmsTag= tagsDao.getSmsTag(smsTag.getTimeStamp());
        if (auxSmsTag.getNumber().equals(smsTag.getNumber())  && auxSmsTag.getTimeStamp() == smsTag.getTimeStamp()){
            callback.onSuccess();
        }else{
            callback.onError();
        }
    }

    @Override
    public void updateUrlTag(UrlTag urlTag, OnTagUpdatedCallback callback) {
        tagsDao.updateUrlTag(urlTag);
        tagsDao.updateTag(new MyTag(urlTag.getTimeStamp(), urlTag.getContent(), AppConstants.TagTypes.URL.toString()));

        final UrlTag auxUrlTag = tagsDao.getUrlTag(urlTag.getTimeStamp());
        if (auxUrlTag.getContent().equals(urlTag.getContent())  && auxUrlTag.getTimeStamp() == urlTag.getTimeStamp()){
            callback.onSuccess();
        }else{
            callback.onError();
        }
    }

    @Override
    public void updateWifiTag(WifiTag wifiTag, OnTagUpdatedCallback callback) {
        tagsDao.updateWifiTag(wifiTag);
        tagsDao.updateTag(new MyTag(wifiTag.getTimeStamp(), wifiTag.getSsid(), AppConstants.TagTypes.WIFI.toString()));

        final WifiTag auxWifiTag = tagsDao.getWifiTag(wifiTag.getTimeStamp());
        if (auxWifiTag.getSsid().equals(wifiTag.getSsid())  && auxWifiTag.getTimeStamp() == wifiTag.getTimeStamp()){
            callback.onSuccess();
        }else{
            callback.onError();
        }
    }
}
