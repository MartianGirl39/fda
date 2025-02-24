package com.example.todolistapp.model

import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.Table

@Table("todo_tag")
class TodoTag(private val todo: Long, private val tag: Long): DataModel {
    fun getTodo() = todo
    fun getTag() = tag
}