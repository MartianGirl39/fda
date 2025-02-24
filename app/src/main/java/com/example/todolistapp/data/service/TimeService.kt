package com.example.todolistapp.data.service

import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.TimeDao

class TimeService: ModelService() {

    private val timeDao = ModelDao.getInstance(TimeDao::class.java)

    override fun selectById(id: Long): Any? = timeDao?.selectById(id)
    override fun selectAll(): List<DataModel>? = timeDao?.selectAll()
    override fun select(query: Query): List<DataModel>? = timeDao?.select(query)
    override fun insert(model: DataModel) { timeDao?.insert(model) }
    override fun update(model: DataModel) { timeDao?.update(model) }
    override fun delete(model: DataModel) { timeDao?.delete(model) }
}