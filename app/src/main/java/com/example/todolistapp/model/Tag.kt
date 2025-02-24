package com.example.todolistapp.model

import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.Id

class Tag(private val name: String, private val color: String, @Id()private val id: Long = -1): DataModel {
    fun getName() = name
    fun getColor() = color
    fun getId() = id
}