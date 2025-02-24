package com.example.todolistapp.data.dao

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.model.DateItem
import com.example.todolistapp.model.DueDate
import com.example.todolistapp.model.Time
import kotlin.reflect.KClass

class DueDateDao(context: Context): ModelDao<DueDate>(context) {
    private val dateDao = ModelDao.getInstance<DateItem, DateDao>(DateDao::class.java)
    private val timeDao = ModelDao.getInstance<Time, TimeDao>(TimeDao::class.java)

    override fun getTypeCopy(): KClass<out DueDate> = DueDate::class
    override fun mapFromCursor(cursor: Cursor): DueDate {
        val date = cursor.getLong(cursor.getColumnIndexOrThrow("date"))
        val time = cursor.getLong(cursor.getColumnIndexOrThrow("time"))
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        return DueDate(dateDao.selectById(date) ?: DateItem.today(), timeDao.selectById(time) ?: Time.now(), id)
    }
}