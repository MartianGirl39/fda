package com.example.todolistapp.data.dao

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.Query
import com.example.todolistapp.model.Tag
import kotlin.reflect.KClass

class TagDao(context: Context): ModelDao<Tag>(context) {
    override fun getTypeCopy(): KClass<out Tag> = Tag::class
    override fun select(query: Query): List<Tag> {
        return super.select(query) as List<Tag>
    }
    override fun mapFromCursor(cursor: Cursor): Tag {
        val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
        val color = cursor.getString(cursor.getColumnIndexOrThrow("color"))
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        return Tag(name, color, id)
    }
}