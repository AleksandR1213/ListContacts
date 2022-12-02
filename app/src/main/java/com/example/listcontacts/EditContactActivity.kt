package com.example.listcontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditContactActivity : AppCompatActivity() {

    var id = 0L
    lateinit var EditName: EditText
    lateinit var EditLastName: EditText
    lateinit var EditNumber: EditText
    lateinit var EditDateBorn: EditText
    lateinit var EditBtnCancel: Button
    lateinit var EditBtnSave: Button

    companion object {
        const val EDIT = "EDIT"
    }

    private val dao by lazy {
        ContactDatabase.getDatabase(this).todoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        EditName = findViewById(R.id.EditName)
        EditLastName = findViewById(R.id.EditLastName)
        EditNumber = findViewById(R.id.EditNumber)
        EditDateBorn = findViewById(R.id.EditDateBorn)
        EditBtnCancel = findViewById(R.id.EditBtnCancel)
        EditBtnSave = findViewById(R.id.EditBtnSave)

        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0L)
        val entity = dao.getById(id)

        EditName.setText(entity.name)
        EditLastName.setText(entity.lastname)
        EditNumber.setText(entity.number)
        EditDateBorn.setText(entity.dateborn)

        EditBtnCancel.setOnClickListener {
            finish()
        }

        EditBtnSave.setOnClickListener {
            println(entity)
            entity.name = EditName.text.toString()
            entity.lastname = EditLastName.text.toString()
            entity.number = EditNumber.text.toString()
            entity.dateborn = EditDateBorn.text.toString()

            dao.update(entity)
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            startActivity(intent)
        }
    }
}