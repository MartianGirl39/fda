package com.example.todolistapp.data.dao

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.model.TodoTag
import kotlin.reflect.KClass

class TodoTagDao(context: Context): ModelDao(context) {
    override fun getTypeCopy(): KClass<out DataModel> = TodoTag::class
    override fun mapFromCursor(cursor: Cursor): DataModel {
        val todo = cursor.getLong(cursor.getColumnIndexOrThrow("todo"))
        val tag = cursor.getLong(cursor.getColumnIndexOrThrow("tag"))
        return TodoTag(todo, tag)
    }
}