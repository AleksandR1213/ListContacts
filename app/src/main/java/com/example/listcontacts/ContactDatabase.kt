package com.example.listcontacts

import android.content.Context
import androidx.room.*

@Database(
    entities = [ContactEntity::class],
    version = 1
)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun todoDao(): ContactDao

    companion object {

        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): ContactDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ContactDatabase::class.java,
                "contactsdb"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}