package com.easynfc.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.net.Uri;

import com.easynfc.data.AarTag;
import com.easynfc.data.EmailTag;
import com.easynfc.data.LocationTag;
import com.easynfc.data.MyTag;
import com.easynfc.data.PhoneTag;
import com.easynfc.data.SmsTag;
import com.easynfc.data.TextTag;
import com.easynfc.data.UrlTag;
import com.easynfc.data.WifiTag;
import com.easynfc.data.source.local.tags.TagsDao;

/**
 * Created by pablorojas on 4/2/18.
 */

@Database(entities = {MyTag.class, TextTag.class, UrlTag.class, SmsTag.class, PhoneTag.class, AarTag.class, LocationTag.class, WifiTag.class, EmailTag.class}, version = 9, exportSchema = true)
public abstract class EasyNfcDatabase extends RoomDatabase {

    private static EasyNfcDatabase INSTANCE;

    public abstract TagsDao tagsDao();

    public static EasyNfcDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), EasyNfcDatabase.class, "easy-nfc-database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}