package com.pcsalt.example.databasedemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pcsalt.example.databasedemo.db.entity.Contact
import com.pcsalt.example.databasedemo.db.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        btn_insert.setOnClickListener {
            if (isValidInfo()) {
                insert()
                clearFields()
            } else {
                Toast.makeText(this@MainActivity, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
        btn_update.setOnClickListener {
            if (isValidInfo()) {
                update()
                clearFields()
            } else {
                Toast.makeText(this@MainActivity, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
        btn_delete.setOnClickListener {
            if (isValidInfo()) {
                delete()
                clearFields()
            } else {
                Toast.makeText(this@MainActivity, "Enter all details", Toast.LENGTH_SHORT).show()
            }
        }
        btn_select.setOnClickListener { select() }
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
        val contact = Contact(null, getName(), getContact(), Date().time)
        contactViewModel.insert(contact)
    }

    private fun update() {
        contactViewModel.update(getName(), getContact())
    }

    private fun delete() {
        contactViewModel.delete(getName())
    }

    private fun select() {
        contactViewModel.getAll().observe(this, Observer {
            tv_content.text = ""
            for (contact in it) {
                tv_content.append("[id: ${contact.id}]\t[name: ${contact.name}]\t[contact: ${contact.contact}]\t[date: ${contact.createdOn}]")
                tv_content.append("\n\n")
            }
        })
    }

}