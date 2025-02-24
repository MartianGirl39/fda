package com.example.todolistapp.data.service

import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.TodoDao
import com.example.todolistapp.model.DateItem
import com.example.todolistapp.model.TodoItem

class TodoService: ModelService<TodoItem>() {

    private val todoDao = ModelDao.getInstance(TodoDao::class.java)

    fun selectByMonth(dateItem: DateItem) = (todoDao).getTodoForMonth(dateItem)
    fun selectByDay(dateItem: DateItem) = (todoDao).getTodoForDay(dateItem)

    override fun selectById(id: Long): Any? = todoDao.selectById(id)
    override fun selectAll(): List<TodoItem>? = todoDao.selectAll()
    override fun select(query: Query): List<TodoItem>? = todoDao.select(query)
    override fun insert(model: TodoItem) { todoDao.insert(model) }
    override fun update(model: TodoItem) { todoDao.update(model) }
    override fun delete(model: TodoItem) { todoDao.delete(model) }
}