package com.example.todolistapp.data.dao

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.model.Time
import kotlin.reflect.KClass

class TimeDao(context: Context): ModelDao(context) {
    override fun getTypeCopy(): KClass<out DataModel> = Time::class
    override fun mapFromCursor(cursor: Cursor): Time {
        val minute = cursor.getInt(cursor.getColumnIndexOrThrow("minute"))
        val hour = cursor.getInt(cursor.getColumnIndexOrThrow("hour"))
        val time = cursor.getString(cursor.getColumnIndexOrThrow("timeOfDay"))
        return Time(hour, minute, Time.Companion.TIME_OF_DAY.valueOf(time))
    }
}