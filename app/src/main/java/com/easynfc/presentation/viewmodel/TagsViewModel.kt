package com.easynfc.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.easynfc.data.model.Text
import com.easynfc.data.source.TagsRepository

class TagsViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: TagsRepository = TagsRepository(application)

    private var listText: LiveData<List<Text>> = repository.getAll()

    fun insert(text: Text) {
        repository.insert(text)
    }

    fun deleteByID(id: Int) {
        repository.deleteByID(id)
    }

    fun getAll(): LiveData<List<Text>> {
        return listText
    }
}