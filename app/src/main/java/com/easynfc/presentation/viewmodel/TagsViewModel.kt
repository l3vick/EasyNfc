package com.easynfc.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.easynfc.data.model.BaseTag
import com.easynfc.data.model.Email
import com.easynfc.data.model.Text
import com.easynfc.data.source.TagsRepository

class TagsViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: TagsRepository = TagsRepository(application)

    private var listText: LiveData<List<Text>> = repository.getAllText()

    private var listEmail: LiveData<List<Email>> = repository.getAllEmail()


    //-------TEXT--------
    fun insertText(text: Text) {
        repository.insertText(text)
    }

    fun deleteTextByID(id: Int) {
        repository.deleteTextByID(id)
    }

    fun getAllText(): LiveData<List<Text>> {
        return listText
    }

    fun deleteAllText() {
        repository.deleteAllText()
    }

    //-------EMAIL--------
    fun insertEmail(email: Email) {
        repository.insertEmail(email)
    }

    fun deleteEmailByID(id: Int) {
        //repository.deleteTextByID(id)
    }

    fun getAllEmail(): LiveData<List<Email>> {
        return listEmail
    }

    fun deleteAllEmail() {
        //repository.deleteAllText()
    }
}