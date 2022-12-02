package com.example.listcontacts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateContactActivity : AppCompatActivity() {

    var id = 0L
    lateinit var CreateName : EditText
    lateinit var CreateLastName : EditText
    lateinit var CreateNumber : EditText
    lateinit var CreateDateBorn : EditText
    lateinit var CreateBtnCreate : Button
    lateinit var CreateBtnCancel : Button

    companion object {
        const val CANCEL = "CANCEL"
    }

    private val dao by lazy {
        ContactDatabase.getDatabase(this).todoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)

        CreateName = findViewById(R.id.CreateName)
        CreateLastName = findViewById(R.id.CreateLastName)
        CreateNumber = findViewById(R.id.CreateNumber)
        CreateDateBorn = findViewById(R.id.CreateDateBorn)
        CreateBtnCreate = findViewById(R.id.CreateBtnCreate)
        CreateBtnCancel = findViewById(R.id.CreateBtnCancel)

        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0L)
        val entity = dao.getById(id)

        var intent = Intent(this, MainActivity::class.java)

        CreateBtnCreate.setOnClickListener {
            entity.name = CreateName.text.toString()
            entity.lastname = CreateLastName.text.toString()
            entity.number = CreateNumber.text.toString()
            entity.dateborn = CreateDateBorn.text.toString()
            dao.update(entity)

            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            startActivity(intent)
        }

        CreateBtnCancel.setOnClickListener{
            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            intent.putExtra(CANCEL, true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }

}