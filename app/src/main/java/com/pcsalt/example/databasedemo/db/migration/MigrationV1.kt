package com.pcsalt.example.databasedemo.db.migration

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pcsalt.example.databasedemo.ext.getLongOrEmpty
import com.pcsalt.example.databasedemo.ext.getStringOrEmpty
import java.text.SimpleDateFormat
import java.util.*

class MigrationV1 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE tblContact_temp (
                _id INTEGER, name TEXT, contact TEXT, created_on INTEGER,
                PRIMARY KEY(_id)
            )
        """.trimIndent()
        )

        val cursor = database.query("SELECT * FROM tblContact")
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val values = ContentValues()
                values.put("_id", cursor.getLongOrEmpty("_id"))
                values.put("name", cursor.getStringOrEmpty("name"))
                values.put("contact", cursor.getStringOrEmpty("contact"))

                val createdOnStr = cursor.getStringOrEmpty("created_on")
                values.put("created_on", getDateMillis(createdOnStr))

                database.insert("tblContact_temp", SQLiteDatabase.CONFLICT_REPLACE, values)
            }
            cursor.close()
        }
        database.execSQL("DROP TABLE tblContact")
        database.execSQL("ALTER TABLE tblContact_temp RENAME TO tblContact")
    }

    private fun getDateMillis(rawDateString: String?): Long {
        if (rawDateString == null) {
            return 0
        } else {
            try {
                val formatter = SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy",
                    Locale.getDefault()
                )
                val rawDate = formatter.parse(rawDateString)
                return rawDate?.time ?: 0
            } catch (e: Exception) {
                e.printStackTrace()
                return 0
            }
        }
    }
}