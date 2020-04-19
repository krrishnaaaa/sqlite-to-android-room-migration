package com.pcsalt.example.databasedemo

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var myDatabase: MyDatabase? = null
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDatabase = MyDatabase(this)
        myDatabase?.open()

        btn_save.setOnClickListener {
            if (isValidInfo()) {
                saveInfo()
            } else {
                Toast.makeText(this@MainActivity, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        myDatabase?.close()
        myDatabase = null
        super.onDestroy()
    }

    private fun isValidInfo(): Boolean {
        return (et_name.text.toString().isNotEmpty()) && (et_contact.text.toString().isNotEmpty())
    }

    private fun saveInfo() {
        val contentValues = ContentValues()
        contentValues.put(DBConsts.NAME, et_name.text.toString())
        contentValues.put(DBConsts.CONTACT, et_contact.text.toString())
        contentValues.put(DBConsts.CREATED_ON, Date().toString())
        val id = myDatabase?.insert(DBConsts.TBL_CONTACT, contentValues)
        Log.d(TAG, "inserted id: $id")
    }


}