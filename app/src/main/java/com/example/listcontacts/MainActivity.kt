package com.example.listcontacts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var SearchContact : EditText
    lateinit var BtnCreate : Button
    private val ContactsList = mutableListOf<ContactEntity>()
    private var hideList = mutableListOf<ContactEntity>()
    private lateinit var adapter: RecycleAdapter
    lateinit var recycleView: RecyclerView

    private val dao by lazy {
        ContactDatabase.getDatabase(this).todoDao()
    }

    companion object {
        const val REQUEST_CODE_INFO = 1
        const val REQUEST_CODE_CREATE = 2
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SearchContact = findViewById(R.id.MainSearchContact)
        BtnCreate = findViewById(R.id.MainBtnAdd)
        recycleView = findViewById(R.id.MainListContact)

        update_list()

        BtnCreate.setOnClickListener{
            var add_id = CreateContact("","", "", "")
            var intent = Intent(this, CreateContactActivity::class.java)

            intent.putExtra(ITEM_ID_KEY, add_id)
            startActivityForResult(intent, REQUEST_CODE_CREATE)
        }

        SearchContact.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                filter(SearchContact.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }

    fun filter(text: String){
        if(text == ""){
            update_list()
        }
        else {
            hideList.clear()
            hideList.addAll(dao.all)

            ContactsList.clear()

            for (contact in hideList) {
                if (text.toLowerCase() in contact.name.toLowerCase()) {
                    if(!ContactsList.contains(contact)) {
                        ContactsList.add(contact)
                    }
                }
            }
            update_list_filtered(ContactsList)
        }
    }

    fun update_list_filtered(filters: MutableList<ContactEntity>){
        adapter = RecycleAdapter(filters) {
            if(it != -1) {
                val intent = Intent(this, InformationContactActivity::class.java)
                intent.putExtra(ITEM_ID_KEY, ContactsList[it].id)
                startActivityForResult(intent, REQUEST_CODE_INFO)
            }

        }
        adapter.notifyItemInserted(filters.lastIndex)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter
    }

    fun update_list(){
        ContactsList.clear()
        ContactsList.addAll(dao.all)

        adapter = RecycleAdapter(ContactsList) {
            if(it != -1) {
                val intent = Intent(this, InformationContactActivity::class.java)
                intent.putExtra(ITEM_ID_KEY, ContactsList[it].id)
                startActivityForResult(intent, REQUEST_CODE_INFO)
            }

        }
        adapter.notifyItemInserted(ContactsList.lastIndex)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter
    }

    fun CreateContact(name : String, lastName : String, dateBorn : String, number : String) : Long{
        val todoEntity = ContactEntity()

        todoEntity.name = name
        todoEntity.lastname = lastName
        todoEntity.dateborn = dateBorn
        todoEntity.number = number

        todoEntity.id = dao.insert(todoEntity)

        ContactsList.add(todoEntity)
        adapter.notifyItemInserted(ContactsList.lastIndex)

        return todoEntity.id!!
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_INFO && resultCode == Activity.RESULT_OK) {

            val id = data!!.getLongExtra(ITEM_ID_KEY, 0L)
            val isDelete = data!!.getBooleanExtra(InformationContactActivity.DELETE, false)

            if(isDelete){
                val cont = dao.getById(id)

                dao.delete(cont)
                ContactsList.remove(cont)
                adapter.notifyItemRemoved(norm_indexof(id, ContactsList))
                update_list()
            }
        }
        if (requestCode == REQUEST_CODE_CREATE && resultCode == Activity.RESULT_OK) {

            val id = data!!.getLongExtra(ITEM_ID_KEY, 0L)
            val isDelete = data!!.getBooleanExtra(CreateContactActivity.CANCEL, false)

            if(isDelete){
                val cont = dao.getById(id)

                dao.delete(cont)
                ContactsList.remove(cont)
                adapter.notifyItemRemoved(norm_indexof(id, ContactsList))
                update_list()
            }
        }
    }

    fun norm_indexof(id: Long, ContactsList: List<ContactEntity>): Int{
        var index = 0

        for(contact in ContactsList) {
            if (contact.id == id)
                break
            index++
        }
        return index
    }
}