package com.example.todolistapp.data.service

import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.DateDao

class DateService: ModelService() {

    private val dateDao = ModelDao.getInstance(DateDao::class.java)

    override fun selectById(id: Long): Any? = dateDao?.selectById(id)
    override fun selectAll(): List<DataModel>? = dateDao?.selectAll()
    override fun select(query: Query): List<DataModel>? = dateDao?.select(query)
    override fun insert(model: DataModel) { dateDao?.insert(model) }
    override fun update(model: DataModel) { dateDao?.update(model) }
    override fun delete(model: DataModel) { dateDao?.delete(model) }
}