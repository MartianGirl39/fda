package com.example.todolistapp.data.service

import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.DueDateDao
import com.example.todolistapp.model.DueDate

class DueDateService: ModelService<DueDate>() {

    private val dueDateDao = ModelDao.getInstance(DueDateDao::class.java)

    override fun selectById(id: Long): Any? = dueDateDao.selectById(id)
    override fun selectAll(): List<DueDate>? = dueDateDao.selectAll()
    override fun select(query: Query): List<DueDate>? = dueDateDao.select(query)
    override fun insert(model: DueDate) { dueDateDao.insert(model) }
    override fun update(model: DueDate) { dueDateDao.update(model) }
    override fun delete(model: DueDate) { dueDateDao.delete(model) }
}