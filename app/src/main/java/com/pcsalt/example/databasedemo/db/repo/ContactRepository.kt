package com.pcsalt.example.databasedemo.db.repo

import com.pcsalt.example.databasedemo.db.dao.ContactDao
import com.pcsalt.example.databasedemo.db.entity.Contact

class ContactRepository(private val contactDao: ContactDao) {

    fun getAll() = contactDao.getAll()

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun update(name: String, contact: String) {
        contactDao.update(name, contact)
    }

    suspend fun delete(name: String) {
        contactDao.delete(name)
    }
}