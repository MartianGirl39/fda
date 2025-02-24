package com.example.todolistapp.data.dao

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.model.DateItem
import kotlin.reflect.KClass

class DateDao(context: Context): ModelDao<DateItem>(context) {
    override fun getTypeCopy(): KClass<out DateItem> = DateItem::class
    override fun mapFromCursor(cursor: Cursor): DateItem {
        val year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
        val month = cursor.getString(cursor.getColumnIndexOrThrow("month"))
        val day = cursor.getInt(cursor.getColumnIndexOrThrow("day"))
        return DateItem.of(year, DateItem.Month.newInstance(month).getMonthNumber(), day)
    }
}