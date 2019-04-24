package com.easynfc.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.easynfc.data.model.Text


@Dao
interface TagsDao {

    //TEXT
    @Insert
    fun insert(textTags: Text)

    @Update
    fun update(textTag: Text)

    @Query("DELETE FROM Text WHERE id=:id")
    fun delete(id: Int)

    @Query("SELECT * FROM Text WHERE id=:id")
    fun get(id: Int): Text

    @Query("SELECT * FROM Text")
    fun getTextList(): LiveData<List<Text>>

}