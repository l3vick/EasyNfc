package com.easynfc.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easynfc.data.model.*
import com.easynfc.data.source.TagsRepository

class TagsViewModel(var repository: TagsRepository): ViewModel() {

    private val listText: LiveData<List<Text>> = repository.getAllText()

    private val listUrl: LiveData<List<Url>> = repository.getAllUrl()

    private val listEmail: LiveData<List<Email>> = repository.getAllEmail()

    private val listPhone: LiveData<List<Phone>> = repository.getAllPhone()

    private val listLauncher: LiveData<List<Launcher>> = repository.getAllLauncher()

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

    //-------URL--------
    fun insertUrl(url: Url) {
        repository.insertUrl(url)
    }

    fun deleteUrlByID(id: Int) {
        //repository.deleteUrlByID(id)
    }

    fun getAllUrl(): LiveData<List<Url>> {
        return listUrl
    }

    fun deleteAllUrl() {
        //repository.deleteAllText()
    }


    //-------PHONE--------
    fun insertPhone(phone: Phone) {
        repository.insertPhone(phone)
    }

    fun deletePhoneByID(id: Int) {

    }

    fun getAllPhone(): LiveData<List<Phone>> {
        return listPhone
    }

    fun deleteAllPhone() {

    }


    //-------LAUNCHER--------
    fun insertLauncher(launcher: Launcher) {
        repository.insertLauncher(launcher)
    }

    fun deleteLauncherByID(id: Int) {

    }

    fun getAllLauncher(): LiveData<List<Launcher>> {
        return listLauncher
    }

    fun deleteAllLauncher() {

    }

    fun deleteAll() {
        repository.deleteAllText()
        repository.deleteAllUrl()
        repository.deleteAllEmail()
        repository.deleteAllLauncher()
        repository.deleteAllPhone()
    }
}


class TagsViewModelFactory(private val repository: TagsRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TagsViewModel(repository) as T
    }
}