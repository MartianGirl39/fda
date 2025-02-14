package com.example.todolistapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocalStorageManager(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
//        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "todo_application_database.db"
        private const val DATABASE_VERSION = 1

        @Volatile
        private var instance: LocalStorageManager? = null

        fun getInstance(context: Context): LocalStorageManager {
            return instance ?: synchronized(this) {
                instance ?: LocalStorageManager(context).also { instance = it }
            }
        }
    }
}
