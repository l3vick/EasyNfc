package com.easynfc.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.easynfc.data.Text;
import com.easynfc.data.source.local.tags.TagsDao;

/**
 * Created by pablorojas on 4/2/18.
 */

@Database(entities = {Text.class}, version = 5, exportSchema = true)
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