package com.easynfc.data.source

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.easynfc.data.model.Text
import com.easynfc.data.source.local.TagsDao
import com.easynfc.data.source.local.TagsDatabase

class TagsRepository(application: Application){

    private var tagsDao: TagsDao

    private var textList: LiveData<List<Text>>


    init {
         val database: TagsDatabase = TagsDatabase.getInstance(application.applicationContext)!!
        tagsDao = database.tagsDao()
        textList = tagsDao.getTextList()
    }


    fun insert(text: Text) {
        val insertTextAsyncTask = InsertTextAsyncTask(tagsDao).execute(text)
    }

    fun deleteByID(id: Int){
        val deleteTextAsyncTask = DeleteTextByIdAsyncTask(tagsDao).execute()
    }

    fun getAll(): LiveData<List<Text>> {
        return textList
    }


    private class InsertTextAsyncTask(tagsDao: TagsDao): AsyncTask<Text, Unit, Unit>(){

        val tagsDao = tagsDao

        override fun doInBackground(vararg params: Text?) {
            tagsDao.insert(params[0]!!)
        }
    }

    private class DeleteTextByIdAsyncTask(val tagsDao: TagsDao): AsyncTask<Int, Unit, Unit>(){

        override fun doInBackground(vararg params: Int?) {
            tagsDao.delete(params[0]!!)
        }
    }

}