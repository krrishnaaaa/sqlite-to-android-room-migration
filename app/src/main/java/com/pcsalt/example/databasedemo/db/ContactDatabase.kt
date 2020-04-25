package com.pcsalt.example.databasedemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pcsalt.example.databasedemo.db.dao.ContactDao
import com.pcsalt.example.databasedemo.db.entity.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun getContactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getContactDatabase(context: Context): ContactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "dbname.db"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}