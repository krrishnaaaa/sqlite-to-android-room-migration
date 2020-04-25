package com.pcsalt.example.databasedemo.db.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pcsalt.example.databasedemo.db.ContactDatabase
import com.pcsalt.example.databasedemo.db.entity.Contact
import com.pcsalt.example.databasedemo.db.repo.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private var contactRepository: ContactRepository

    init {
        val contactDao = ContactDatabase.getContactDatabase(application).getContactDao()
        contactRepository = ContactRepository(contactDao)
    }

    fun getAll() = contactRepository.getAll()

    fun insert(contact: Contact) = viewModelScope.launch {
        contactRepository.insert(contact)
    }

    fun update(name: String, contact: String) = viewModelScope.launch {
        contactRepository.update(name, contact)
    }

    fun delete(name: String) = viewModelScope.launch {
        contactRepository.delete(name)
    }
}