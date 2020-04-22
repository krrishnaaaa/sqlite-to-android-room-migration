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

        btn_insert.setOnClickListener {
            if (isValidInfo()) {
                insert()
                clearFields()
            } else {
                Toast.makeText(this@MainActivity, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
        btn_update.setOnClickListener{
            if (isValidInfo()) {
                update()
                clearFields()
            } else {
                Toast.makeText(this@MainActivity, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
        btn_delete.setOnClickListener{
            if (isValidInfo()) {
                delete()
                clearFields()
            } else {
                Toast.makeText(this@MainActivity, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
        btn_select.setOnClickListener{ select() }
    }

    override fun onDestroy() {
        myDatabase?.close()
        myDatabase = null
        super.onDestroy()
    }

    private fun getName() = et_name.text.toString()

    private fun getContact() = et_contact.text.toString()

    private fun isValidInfo(): Boolean {
        return (getName().isNotEmpty()) && (getContact().isNotEmpty())
    }

    private fun clearFields() {
        et_name.setText("")
        et_contact.setText("")
    }

    private fun insert() {
        val contentValues = ContentValues()
        contentValues.put(DBConsts.NAME, et_name.text.toString())
        contentValues.put(DBConsts.CONTACT, et_contact.text.toString())
        contentValues.put(DBConsts.CREATED_ON, Date().toString())
        val id = myDatabase?.insert(DBConsts.TBL_CONTACT, contentValues)
        Log.d(TAG, "inserted id: $id")
    }

    private fun update() {
        val whereClause: String = DBConsts.NAME + "=?"
        val whereArgs = arrayOf(getName())

        val contentValues = ContentValues()
        contentValues.put(DBConsts.CONTACT, getContact())

        val updatedRows = myDatabase?.update(DBConsts.TBL_CONTACT, contentValues, whereClause, whereArgs)
        Log.d(TAG, "updated rows $updatedRows")
    }

    private fun delete() {
        val whereClause: String = DBConsts.NAME+ "=?" + DBConsts.AND + DBConsts.CONTACT + "=?"
        val whereArgs = arrayOf(
            getName(),
            getContact()
        )
        val deletedRows = myDatabase?.delete(DBConsts.TBL_CONTACT, whereClause, whereArgs)
        Log.d(TAG,"Number of deleted rows: $deletedRows")
    }

    private fun select() {
        val result = StringBuilder()
        var id: Int
        var name: String
        var contact: String
        val columns = arrayOf(
            DBConsts.ID,
            DBConsts.NAME,
            DBConsts.CONTACT
        )
        val selection: String? = null // no selection
        val selectionArgs: Array<String>? = null // not needed
        val cur = myDatabase?.select(DBConsts.TBL_CONTACT, columns, selection, selectionArgs)
        //check if cursor returned from select statement is not null
        if (cur != null) {
            while (cur.moveToNext()) {
                id = cur.getInt(cur.getColumnIndex(DBConsts.ID))
                name = cur.getString(cur.getColumnIndex(DBConsts.NAME))
                contact = cur.getString(cur.getColumnIndex(DBConsts.CONTACT))
                result.append("[id: $id]\t[name: $name]\t[contact: $contact]\n")
            }
        } else {
            result.append("No result found")
        }
        tv_content.text = result
    }

}