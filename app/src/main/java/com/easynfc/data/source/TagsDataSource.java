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

public interface TagsDataSource {

    void getTags(LoadTagsCallback callback);

    void addText(TextTag textTag, OnTagSavedCallback callback);

    void addUrl(UrlTag urlTag, OnTagSavedCallback callback);

    void addSms(SmsTag smsTag, OnTagSavedCallback callback);

    void addPhone(PhoneTag phoneTag, OnTagSavedCallback callback);

    void addAar(AarTag aarTag, OnTagSavedCallback callback);

    void addLocation(LocationTag locationTag, OnTagSavedCallback callback);

    void addWifi(WifiTag wifiTag, OnTagSavedCallback callback);

    void addEmail(EmailTag emailTag, OnTagSavedCallback callback);

    void deleteTag(MyTag myTag, MyTagsContract.OnDeleteTagCallback callback);

    void getTextTag(long timestamp, SimpleTextWriterContract.LoadTextTagCallback callback);

    void getAarTag(long tagId, AppLauncherWriterContract.LoadAarTagCallback callback);

    void getEmailTag(long tagId, EmailWriterContract.LoadEmailTagCallback callback);

    void getLocationTag(long tagId, LocationWriterContract.LoadLocationTagCallback callback);

    void getPhoneTag(long tagId, PhoneWriterContract.LoadPhoneTagCallback callback);

    void getSmsTag(long tagId, SmsWriterContract.LoadSmsTagCallback callback);

    void getUrlTag(long tagId, UrlWriterContract.LoadUrlTagCallback callback);

    void getWifiTag(long tagId, WiFiWriterContract.LoadWifiTagCallback callback);

    interface LoadTagsCallback {

        void onTagsLoaded(List<MyTag> tags);

        void onDataNotAvailable();
    }

    interface OnTagSavedCallback {
        void onSuccess();
        void onError();
    }

}
