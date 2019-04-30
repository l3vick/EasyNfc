package com.easynfc.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.easynfc.data.model.*


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


    //URL
    @Insert
    fun insertUrl(url: Url)

    @Update
    fun updateUrl(url: Url)

    @Query("DELETE FROM Url WHERE id=:id")
    fun deleteUrl(id: Int)

    @Query("DELETE FROM Url")
    fun deleteAllUrl()

    @Query("SELECT * FROM Url WHERE id=:id")
    fun getUrlByID(id: Int): Text

    @Query("SELECT * FROM Url")
    fun getUrlList(): LiveData<List<Url>>

    //PHONE
    @Insert
    fun insertPhone(phone: Phone)

    @Update
    fun updatePhone(phone: Phone)

    @Query("DELETE FROM Phone WHERE id=:id")
    fun deletePhone(id: Int)

    @Query("DELETE FROM Phone")
    fun deleteAllPhone()

    @Query("SELECT * FROM Phone WHERE id=:id")
    fun getPhoneByID(id: Int): Text

    @Query("SELECT * FROM Phone")
    fun getPhoneList(): LiveData<List<Phone>>


    //LAUNCHER
    @Insert
    fun insertLauncher(url: Launcher)

    @Update
    fun updateUrl(url: Launcher)

    @Query("DELETE FROM Url WHERE id=:id")
    fun deleteLauncher(id: Int)

    @Query("DELETE FROM Launcher")
    fun deleteAllLauncher()

    @Query("SELECT * FROM Launcher WHERE id=:id")
    fun getLauncherByID(id: Int): Text

    @Query("SELECT * FROM Launcher")
    fun getLauncherList(): LiveData<List<Launcher>>







}