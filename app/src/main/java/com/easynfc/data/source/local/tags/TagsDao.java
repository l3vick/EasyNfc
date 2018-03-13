package com.easynfc.data.source.local.tags;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.easynfc.data.AarTag;
import com.easynfc.data.EmailTag;
import com.easynfc.data.LocationTag;
import com.easynfc.data.PhoneTag;
import com.easynfc.data.SmsTag;
import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;
import com.easynfc.data.MyTag;
import com.easynfc.data.WifiTag;

import java.util.List;

/**
 * Created by pablorojas on 4/2/18.
 */

@Dao
public interface TagsDao {

    @Query("SELECT * FROM MyTag")
    List<MyTag> getTags();

    @Insert
    void addTag(MyTag myTag);

    @Insert
    void insertTextTag(TextTag textTags);

    @Insert
    void insertUrlTag(UrlTag urlTag);

    @Insert
    void insertSmsTag(SmsTag smsTag);

    @Insert
    void insertPhoneTag(PhoneTag phoneTag);

    @Insert
    void insertAarTag(AarTag aarTag);

    @Insert
    void insertLocationTag(LocationTag locationTag);

    @Insert
    void insertWifiTag(WifiTag wifiTag);

    @Insert
    void insertEmailTag(EmailTag emailTag);

}
