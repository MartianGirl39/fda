package com.example.todolistapp.data.dao

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.model.Note
import kotlin.reflect.KClass

class NoteDao(context: Context): ModelDao(context) {
    override fun getTypeCopy(): KClass<out DataModel> = DataModel::class
    override fun mapFromCursor(cursor: Cursor): DataModel {
        val note = cursor.getString(cursor.getColumnIndexOrThrow("message"))
        val todo = cursor.getInt(cursor.getColumnIndexOrThrow("todo"))
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        return Note(note, todo, id)
    }

}