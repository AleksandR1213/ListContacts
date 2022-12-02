package com.example.listcontacts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class InformationContactActivity : AppCompatActivity() {

    var id = 0L

    lateinit var InfoName : TextView
    lateinit var InfoLatName : TextView
    lateinit var InfoDateBorn : TextView
    lateinit var InfoNumber : TextView
    lateinit var InfoBtnEdit : Button
    lateinit var InfoBtnDelete : Button
    lateinit var InfoBtnBack: Button

    companion object {
        const val DELETE = "DELETE"
    }

    private val dao by lazy {
        ContactDatabase.getDatabase(this).todoDao()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_contact)

        InfoName = findViewById(R.id.InfoName)
        InfoLatName = findViewById(R.id.InfoLatName)
        InfoDateBorn = findViewById(R.id.InfoDateBorn)
        InfoNumber = findViewById(R.id.InfoNumber)
        InfoBtnEdit = findViewById(R.id.InfoBtnEdit)
        InfoBtnDelete = findViewById(R.id.InfoBtnDelete)
        InfoBtnBack = findViewById(R.id.InfoBtnBack)

        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0)
        val entity = dao.getById(id)
        println(entity)
        InfoName.text = entity?.name
        InfoLatName.text = entity?.lastname
        InfoDateBorn.text = entity?.number
        InfoNumber.text = entity?.dateborn

        InfoBtnDelete.setOnClickListener{
            var returnIntent = Intent()
            returnIntent.putExtra(MainActivity.ITEM_ID_KEY, id)
            returnIntent.putExtra(DELETE, true)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        InfoBtnEdit.setOnClickListener{
            var intent = Intent(this, EditContactActivity::class.java)

            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            startActivity(intent)
        }

        InfoBtnBack.setOnClickListener{
            finish()
        }
    }
}