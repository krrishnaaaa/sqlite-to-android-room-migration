package com.pcsalt.example.databasedemo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tblContact")
data class Contact(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    var id: Long?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "contact")
    var contact: String?,
    @ColumnInfo(name = "created_on")
    var createdOn: Long?
) {
}