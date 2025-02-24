package com.example.todolistapp.model

import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.Id

class Note(private val message: String, private val todo: Int = -1, @Id()private val id: Long = -1): DataModel {
    fun getMessage() = message
}