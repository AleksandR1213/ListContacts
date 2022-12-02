package com.example.listcontacts

import androidx.room.*

@Dao
interface ContactDao {
    @get:Query("SELECT * FROM contacts")
    val all: List<ContactEntity>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getById(id: Long): ContactEntity

    @Insert
    fun insert(todo: ContactEntity): Long

    @Update
    fun update(todo: ContactEntity)

    @Delete
    fun delete(todo: ContactEntity)

}