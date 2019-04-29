package com.easynfc.data.source

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.easynfc.data.model.Email
import com.easynfc.data.model.Text
import com.easynfc.data.source.local.TagsDao
import com.easynfc.data.source.local.TagsDatabase

class TagsRepository(application: Application){

    private var tagsDao: TagsDao

    private var textList: LiveData<List<Text>>
    private var emailList: LiveData<List<Email>>


    init {
         val database: TagsDatabase = TagsDatabase.getInstance(application.applicationContext)!!
        tagsDao = database.tagsDao()
        textList = tagsDao.getTextList()
        emailList= tagsDao.getEmailList()
    }


    fun insertText(text: Text) {
        val insertTextAsyncTask = InsertTextAsyncTask(tagsDao).execute(text)
    }

    fun deleteTextByID(id: Int){
        val deleteTextAsyncTask = DeleteTextByIdAsyncTask(tagsDao).execute()
    }

    fun getAllText(): LiveData<List<Text>> {
        return textList
    }

    fun deleteAllText() {
        val deleteAllTextAsyncTask = DeleteAllTextAsyncTask(tagsDao).execute()
    }


    fun insertEmail(email: Email) {
        val insertEmailAsyncTask = InsertEmailAsyncTask(tagsDao).execute(email)
    }

    fun deleteEmailByID(id: Int){
        val deleteEmailAsyncTask = DeleteEmailByIdAsyncTask(tagsDao).execute()
    }

    fun getAllEmail(): LiveData<List<Email>> {
        return emailList
    }

    fun deleteAllEmail() {
        val deleteAllEmailAsyncTask = DeleteAllEmailAsyncTask(tagsDao).execute()
    }


    private class InsertTextAsyncTask(tagsDao: TagsDao): AsyncTask<Text, Unit, Unit>(){

        val tagsDao = tagsDao

        override fun doInBackground(vararg params: Text?) {
            tagsDao.insertText(params[0]!!)
        }
    }

    private class DeleteTextByIdAsyncTask(val tagsDao: TagsDao): AsyncTask<Int, Unit, Unit>(){

        override fun doInBackground(vararg params: Int?) {
            tagsDao.deleteText(params[0]!!)
        }
    }

    private class DeleteAllTextAsyncTask(val tagsDao: TagsDao): AsyncTask<Unit, Unit, Unit>(){
        override fun doInBackground(vararg params: Unit?) {
            tagsDao.deleteAllText()
        }
    }

    private class InsertEmailAsyncTask(tagsDao: TagsDao): AsyncTask<Email, Unit, Unit>(){

        val tagsDao = tagsDao

        override fun doInBackground(vararg params: Email?) {
            tagsDao.insertEmail(params[0]!!)
        }
    }

    private class DeleteEmailByIdAsyncTask(val tagsDao: TagsDao): AsyncTask<Int, Unit, Unit>(){

        override fun doInBackground(vararg params: Int?) {
            tagsDao.deleteEmail(params[0]!!)
        }
    }

    private class DeleteAllEmailAsyncTask(val tagsDao: TagsDao): AsyncTask<Unit, Unit, Unit>(){
        override fun doInBackground(vararg params: Unit?) {
            tagsDao.deleteAllEmail()
        }
    }

}