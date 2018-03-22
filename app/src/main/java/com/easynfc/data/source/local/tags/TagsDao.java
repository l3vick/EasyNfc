package com.easynfc.data.source.local.tags;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

@Dao
public interface TagsDao {


    //GENERAL TAGS
    @Query("SELECT * FROM MyTag")
    List<MyTag> getTags();

    @Insert
    void insertTag(MyTag myTag);


    @Delete
    void deleteTag(MyTag... myTags);


    //TEXT
    @Insert
    void insertTextTag(TextTag textTags);

    @Query("DELETE FROM TextTag WHERE timestamp=:timestamp")
    void deleteTextTag(long timestamp);

    @Query("SELECT * FROM TextTag WHERE timestamp=:timestamp")
    TextTag getTextTag(long timestamp);

    @Query("SELECT * FROM TextTag")
    List<TextTag> getTextTags();


    //URL
    @Insert
    void insertUrlTag(UrlTag urlTag);

    @Query("DELETE FROM UrlTag WHERE timestamp=:timestamp")
    void deleteUrlTag(long timestamp);

    @Query("SELECT * FROM UrlTag WHERE timestamp=:timestamp")
    UrlTag getUrlTag(long timestamp);


    //SMS
    @Insert
    void insertSmsTag(SmsTag smsTag);

    @Query("DELETE FROM SmsTag WHERE timestamp=:timestamp")
    void deleteSmsTag(long timestamp);

    @Query("SELECT * FROM SmsTag WHERE timestamp=:timestamp")
    SmsTag getSmsTag(long timestamp);


    //PHONE
    @Insert
    void insertPhoneTag(PhoneTag phoneTag);

    @Query("DELETE FROM SmsTag WHERE timestamp=:timestamp")
    void deletePhoneTag(long timestamp);

    @Query("SELECT * FROM PhoneTag WHERE timestamp=:timestamp")
    PhoneTag getPhoneTag(long timestamp);


    //AAR
    @Insert
    void insertAarTag(AarTag aarTag);

    @Query("DELETE FROM AarTag WHERE timestamp=:timestamp")
    void deleteAarTag(long timestamp);

    @Query("SELECT * FROM AarTag WHERE timestamp=:timestamp")
    AarTag getAarTag(long timestamp);


    //LOCATION
    @Insert
    void insertLocationTag(LocationTag locationTag);

    @Query("DELETE FROM LocationTag WHERE timestamp=:timestamp")
    void deleteLocationTag(long timestamp);

    @Query("SELECT * FROM LocationTag WHERE timestamp=:timestamp")
    LocationTag getLocationTag(long timestamp);


    //WIFI
    @Insert
    void insertWifiTag(WifiTag wifiTag);

    @Query("DELETE FROM WifiTag WHERE timestamp=:timestamp")
    void deleteWifiTag(long timestamp);

    @Query("SELECT * FROM WifiTag WHERE timestamp=:timestamp")
    WifiTag getWifiTag(long timestamp);


    //EMAIL
    @Insert
    void insertEmailTag(EmailTag emailTag);

    @Query("DELETE FROM EmailTag WHERE timestamp=:timestamp")
    void deleteEmailTag(long timestamp);

    @Query("SELECT * FROM EmailTag WHERE timestamp=:timestamp")
    EmailTag getEmailTag(long timestamp);



}
