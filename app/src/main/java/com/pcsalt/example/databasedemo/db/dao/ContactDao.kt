package com.pcsalt.example.databasedemo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pcsalt.example.databasedemo.db.entity.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM tblContact")
    fun getAll(): LiveData<List<Contact>>

    @Insert
    suspend fun insert(contact: Contact)

    @Query("UPDATE tblContact SET contact = :contact WHERE name = :name")
    suspend fun update(name: String, contact: String)

    @Query("DELETE FROM tblContact WHERE name = :name")
    suspend fun delete(name: String)
}