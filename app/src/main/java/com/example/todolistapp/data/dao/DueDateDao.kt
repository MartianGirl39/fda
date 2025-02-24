package com.example.todolistapp.data.dao

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.model.DateItem
import com.example.todolistapp.model.DueDate
import com.example.todolistapp.model.Time
import kotlin.reflect.KClass

class DueDateDao(context: Context): ModelDao(context) {
    private val dateDao = ModelDao.getInstance(DueDateDao::class.java)
    private val timeDao = ModelDao.getInstance(TimeDao::class.java)

    override fun getTypeCopy(): KClass<out DataModel> = DueDate::class
    override fun mapFromCursor(cursor: Cursor): DataModel {
        val date = cursor.getLong(cursor.getColumnIndexOrThrow("date"))
        val time = cursor.getLong(cursor.getColumnIndexOrThrow("time"))
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        return DueDate(dateDao?.selectById(date) as DateItem, timeDao?.selectById(time) as Time, id)
    }
}