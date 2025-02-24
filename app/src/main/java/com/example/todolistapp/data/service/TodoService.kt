package com.example.todolistapp.data.service

import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.TodoDao
import com.example.todolistapp.model.DateItem

class TodoService: ModelService() {

    private val todoDao = ModelDao.Companion.getInstance(TodoDao::class.java)

    fun selectByMonth(dateItem: DateItem) = (todoDao as TodoDao).getTodoForMonth(dateItem)
    fun selectByDay(dateItem: DateItem) = (todoDao as TodoDao).getTodoForDay(dateItem)

    override fun selectById(id: Long): Any? = todoDao?.selectById(id)
    override fun selectAll(): List<DataModel>? = todoDao?.selectAll()
    override fun select(query: Query): List<DataModel>? = todoDao?.select(query)
    override fun insert(model: DataModel) { todoDao?.insert(model) }
    override fun update(model: DataModel) { todoDao?.update(model) }
    override fun delete(model: DataModel) { todoDao?.delete(model) }
}