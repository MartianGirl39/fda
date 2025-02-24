package com.example.todolistapp.data.service

import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.TimeDao
import com.example.todolistapp.model.Time

class TimeService: ModelService<Time>() {

    private val timeDao = ModelDao.getInstance(TimeDao::class.java)

    override fun selectById(id: Long): Any? = timeDao.selectById(id)
    override fun selectAll(): List<Time>? = timeDao.selectAll()
    override fun select(query: Query): List<Time>? = timeDao.select(query)
    override fun insert(model: Time) { timeDao.insert(model) }
    override fun update(model: Time) { timeDao.update(model) }
    override fun delete(model: Time) { timeDao.delete(model) }
}