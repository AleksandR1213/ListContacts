package com.example.listcontacts

import androidx.room.*

@Entity(tableName = "contacts")
class ContactEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var name: String = ""
    var lastname: String = ""
    var dateborn: String = ""
    var number: String = ""
}