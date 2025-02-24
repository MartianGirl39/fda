package com.example.todolistapp.model

import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.Id
import com.example.todolistapp.data.JOIN
import com.example.todolistapp.data.dao.DueDateDao

class DueDate(@JOIN(onModel = DateItem::class)private val date: DateItem, @JOIN(onModel = Time::class)private val time: Time, @Id()private val id: Long = -1): DataModel {
    fun getDate() = date
    fun getTime() = time
}