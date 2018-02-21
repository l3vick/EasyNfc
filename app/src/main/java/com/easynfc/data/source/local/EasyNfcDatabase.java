package com.easynfc.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.easynfc.data.Menu;
import com.easynfc.data.TagMenu;
import com.easynfc.data.source.local.menu.MenuDao;

/**
 * Created by pablorojas on 4/2/18.
 */

@Database(entities = {Menu.class, TagMenu.class}, version = 2, exportSchema = true)
public abstract class EasyNfcDatabase extends RoomDatabase {

    private static EasyNfcDatabase INSTANCE;

    public abstract MenuDao menuDao();

    public static EasyNfcDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), EasyNfcDatabase.class, "easy-nfc-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}