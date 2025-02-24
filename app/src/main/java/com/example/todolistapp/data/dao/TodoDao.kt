package com.example.todolistapp.data.dao

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.Query
import com.example.todolistapp.model.DateItem
import com.example.todolistapp.model.DueDate
import com.example.todolistapp.model.Note
import com.example.todolistapp.model.Tag
import com.example.todolistapp.model.TodoItem
import com.example.todolistapp.model.TodoList
import com.example.todolistapp.model.TodoTag
import kotlin.reflect.KClass

class TodoDao(context: Context): ModelDao(context) {
    override fun getTypeCopy(): KClass<TodoItem> = TodoItem::class

    private val dueDateDao = ModelDao.getInstance(DueDateDao::class.java)
    private val tagDao = ModelDao.getInstance(TagDao::class.java)
    private val todoTagDao = ModelDao.getInstance(TodoTagDao::class.java)
    private val noteDao = ModelDao.getInstance(NoteDao::class.java)

    fun getTodoForDay(date: DateItem): List<DataModel>? {
        return super.select(Query.newQueryBuilder(this.getTypeCopy()).setFilter("due_date.day = ?, due_date.month = ? && due_date.year = ?").setFilteringArgs(arrayOf(date.getDayOfMonth().toString(), date.getMonth().toString(), date.getYear().toString())).build())
    }

    fun getTodoForMonth(date: DateItem): List<DataModel>? {
        return super.select(Query.newQueryBuilder(this.getTypeCopy()).setFilter("due_date.month = ? && due_date.year = ?").setFilteringArgs(arrayOf(date.getMonth().toString(), date.getYear().toString())).build())
    }

    override fun mapFromCursor(cursor: Cursor): TodoItem {
        val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
        val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
        val urgency = cursor.getString(cursor.getColumnIndexOrThrow("urgency"))
        val importance = cursor.getString(cursor.getColumnIndexOrThrow("importance"))
        val dueDate = dueDateDao?.mapFromCursor(cursor)
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        val todoTags = todoTagDao?.select(Query.newQueryBuilder(TodoTag::class).setFilter("todo = ?").setFilteringArgs(arrayOf(id.toString())).build()) as List<TodoTag>
        val tags = todoTags.map { tagDao?.selectById(it.getTag()) }
        val notes = noteDao?.select(query = Query.newQueryBuilder(Note::class).setFilter("todo = ?").setFilteringArgs(arrayOf(id.toString())).build())
        val isFinished = cursor.getString(cursor.getColumnIndexOrThrow("isFinished"))
        return TodoItem.getBuilder(name, dueDate as DueDate)
            .description(description)
            .urgency(TodoItem.Degree.valueOf(urgency)) 
            .importance(TodoItem.Degree.valueOf(importance))
            .tags(tags as List<Tag>)
            .notes(notes as List<Note>)
            .isFinished(isFinished == "true")
            .id(id)
            .build()
    }
}