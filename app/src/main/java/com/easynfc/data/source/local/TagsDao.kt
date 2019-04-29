package com.easynfc.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.easynfc.data.model.Email
import com.easynfc.data.model.Text


@Dao
interface TagsDao {

    //TEXT
    @Insert
    fun insertText(text: Text)

    @Update
    fun updateText(text: Text)

    @Query("DELETE FROM Text WHERE id=:id")
    fun deleteText(id: Int)

    @Query("DELETE FROM Text")
    fun deleteAllText()

    @Query("SELECT * FROM Text WHERE id=:id")
    fun getTextByID(id: Int): Text

    @Query("SELECT * FROM Text")
    fun getTextList(): LiveData<List<Text>>

    //EMAIL
    @Insert
    fun insertEmail(email: Email)

    @Update
    fun updateEmail(email: Email)

    @Query("DELETE FROM Email WHERE id=:id")
    fun deleteEmail(id: Int)

    @Query("DELETE FROM Email")
    fun deleteAllEmail()

    @Query("SELECT * FROM Email WHERE id=:id")
    fun getEmailByID(id: Int): Text

    @Query("SELECT * FROM Email")
    fun getEmailList(): LiveData<List<Email>>









}