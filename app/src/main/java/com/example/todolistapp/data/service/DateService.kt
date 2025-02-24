package com.example.todolistapp.data.service

import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.DateDao
import com.example.todolistapp.model.DateItem

class DateService: ModelService<DateItem>() {

    private val dateDao = ModelDao.getInstance(DateDao::class.java)

    override fun selectById(id: Long): Any? = dateDao.selectById(id)
    override fun selectAll(): List<DateItem>? = dateDao.selectAll()
    override fun select(query: Query): List<DateItem>? = dateDao.select(query)
    override fun insert(model: DateItem) { dateDao.insert(model) }
    override fun update(model: DateItem) { dateDao.update(model) }
    override fun delete(model: DateItem) { dateDao.delete(model) }
}