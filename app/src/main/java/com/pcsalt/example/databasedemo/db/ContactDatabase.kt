package com.pcsalt.example.databasedemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pcsalt.example.databasedemo.db.converter.Converters
import com.pcsalt.example.databasedemo.db.dao.ContactDao
import com.pcsalt.example.databasedemo.db.entity.Contact
import com.pcsalt.example.databasedemo.db.migration.MigrationV1

@TypeConverters(Converters::class)
@Database(entities = [Contact::class], version = 2)
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
                    .addMigrations(MigrationV1())
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