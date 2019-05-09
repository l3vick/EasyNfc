package com.easynfc.data.source

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.easynfc.data.model.*
import com.easynfc.data.source.local.TagsDao
import com.easynfc.data.source.local.TagsDatabase

class TagsRepository(context: Context) {

    private var tagsDao: TagsDao

    private var textList: LiveData<List<Text>>
    private var emailList: LiveData<List<Email>>
    private var urlList: LiveData<List<Url>>
    private var phoneList: LiveData<List<Phone>>
    private var launcherList: LiveData<List<Launcher>>


    init {
        val database: TagsDatabase = TagsDatabase.getInstance(context)!!
        tagsDao = database.tagsDao()
        textList = tagsDao.getTextList()
        emailList = tagsDao.getEmailList()
        urlList = tagsDao.getUrlList()
        phoneList = tagsDao.getPhoneList()
        launcherList = tagsDao.getLauncherList()
    }

    //-------TEXT--------
    fun insertText(text: Text) {
        InsertTextAsyncTask(tagsDao).execute(text)
    }

    fun deleteTextByID(id: Int) {
        DeleteTextByIdAsyncTask(tagsDao).execute()
    }

    fun getAllText(): LiveData<List<Text>> {
        return textList
    }

    fun deleteAllText() {
        DeleteAllTextAsyncTask(tagsDao).execute()
    }

    //-------EMAIL--------
    fun insertEmail(email: Email) {
        InsertEmailAsyncTask(tagsDao).execute(email)
    }

    fun deleteEmailByID(id: Int) {
        DeleteEmailByIdAsyncTask(tagsDao).execute()
    }

    fun getAllEmail(): LiveData<List<Email>> {
        return emailList
    }

    fun deleteAllEmail() {
        DeleteAllEmailAsyncTask(tagsDao).execute()
    }

    //-------URL--------
    fun insertUrl(url: Url) {
        InsertUrlAsyncTask(tagsDao).execute(url)
    }

    fun deleteUrlByID(id: Int) {
        DeleteUrlByIdAsyncTask(tagsDao).execute()
    }

    fun getAllUrl(): LiveData<List<Url>> {
        return urlList
    }

    fun deleteAllUrl() {
        DeleteAllUrlAsyncTask(tagsDao).execute()
    }

    //-------PHONE--------
    fun insertPhone(phone: Phone) {
        InsertPhoneAsyncTask(tagsDao).execute(phone)
    }

    fun deletePhoneByID(id: Int) {
        DeletePhoneByIdAsyncTask(tagsDao).execute()
    }

    fun getAllPhone(): LiveData<List<Phone>> {
        return phoneList
    }

    fun deleteAllPhone() {
        DeleteAllPhoneAsyncTask(tagsDao).execute()
    }

    //-------LAUNCHER--------
    fun insertLauncher(launcher: Launcher) {
        InsertLauncherAsyncTask(tagsDao).execute(launcher)
    }

    fun deleteLauncherByID(id: Int) {
        DeleteLauncherByIdAsyncTask(tagsDao).execute()
    }

    fun getAllLauncher(): LiveData<List<Launcher>> {
        return launcherList
    }

    fun deleteAllLauncher() {
        DeleteAllLauncherAsyncTask(tagsDao).execute()
    }


    private class InsertTextAsyncTask(tagsDao: TagsDao) : AsyncTask<Text, Unit, Unit>() {

        val tagsDao = tagsDao

        override fun doInBackground(vararg params: Text?) {
            tagsDao.insertText(params[0]!!)
        }
    }

    private class DeleteTextByIdAsyncTask(val tagsDao: TagsDao) : AsyncTask<Int, Unit, Unit>() {

        override fun doInBackground(vararg params: Int?) {
            tagsDao.deleteText(params[0]!!)
        }
    }

    private class DeleteAllTextAsyncTask(val tagsDao: TagsDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            tagsDao.deleteAllText()
        }
    }

    private class InsertEmailAsyncTask(tagsDao: TagsDao) : AsyncTask<Email, Unit, Unit>() {

        val tagsDao = tagsDao

        override fun doInBackground(vararg params: Email?) {
            tagsDao.insertEmail(params[0]!!)
        }
    }

    private class DeleteEmailByIdAsyncTask(val tagsDao: TagsDao) : AsyncTask<Int, Unit, Unit>() {

        override fun doInBackground(vararg params: Int?) {
            tagsDao.deleteEmail(params[0]!!)
        }
    }

    private class DeleteAllEmailAsyncTask(val tagsDao: TagsDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            tagsDao.deleteAllEmail()
        }
    }


    private class InsertUrlAsyncTask(tagsDao: TagsDao) : AsyncTask<Url, Unit, Unit>() {

        val tagsDao = tagsDao

        override fun doInBackground(vararg params: Url?) {
            tagsDao.insertUrl(params[0]!!)
        }
    }

    private class DeleteUrlByIdAsyncTask(val tagsDao: TagsDao) : AsyncTask<Int, Unit, Unit>() {

        override fun doInBackground(vararg params: Int?) {
            tagsDao.deleteUrl(params[0]!!)
        }
    }

    private class DeleteAllUrlAsyncTask(val tagsDao: TagsDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            tagsDao.deleteAllUrl()
        }
    }

    private class InsertPhoneAsyncTask(tagsDao: TagsDao) : AsyncTask<Phone, Unit, Unit>() {

        val tagsDao = tagsDao

        override fun doInBackground(vararg params: Phone?) {
            tagsDao.insertPhone(params[0]!!)
        }
    }

    private class DeletePhoneByIdAsyncTask(val tagsDao: TagsDao) : AsyncTask<Int, Unit, Unit>() {

        override fun doInBackground(vararg params: Int?) {
            tagsDao.deletePhone(params[0]!!)
        }
    }

    private class DeleteAllPhoneAsyncTask(val tagsDao: TagsDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            tagsDao.deleteAllPhone()
        }
    }

    private class InsertLauncherAsyncTask(tagsDao: TagsDao) : AsyncTask<Launcher, Unit, Unit>() {

        val tagsDao = tagsDao

        override fun doInBackground(vararg params: Launcher?) {
            tagsDao.insertLauncher(params[0]!!)
        }
    }

    private class DeleteLauncherByIdAsyncTask(val tagsDao: TagsDao) : AsyncTask<Int, Unit, Unit>() {

        override fun doInBackground(vararg params: Int?) {
            tagsDao.deleteLauncher(params[0]!!)
        }
    }

    private class DeleteAllLauncherAsyncTask(val tagsDao: TagsDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            tagsDao.deleteAllLauncher()
        }
    }

}