package com.easynfc.data.source.local

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.easynfc.data.model.*

@Database(entities = [Text::class, Email::class, Url::class, Phone::class, Launcher::class], version = 8, exportSchema = true)
abstract class TagsDatabase : RoomDatabase() {

    abstract fun tagsDao(): TagsDao

    companion object {

        private  var instance: TagsDatabase? = null

        fun getInstance(context: Context): TagsDatabase? {
            if (instance == null) {
                synchronized(TagsDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            TagsDatabase::class.java, "tags_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build()
                }
            }
            return instance
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

            }
        }

    }

    class PopulateDbAsyncTask(db: TagsDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = db?.tagsDao()

        override fun doInBackground(vararg p0: Unit?) {
            noteDao?.insertText(Text("Title 1", 1343892452L))
            noteDao?.insertText(Text("Title 2", 1343892452L))
            noteDao?.insertText(Text("Title 3", 1343892452L))
        }
    }

}


