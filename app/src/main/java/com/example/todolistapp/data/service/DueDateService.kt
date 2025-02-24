package com.example.todolistapp.data.service

import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.DueDateDao

class DueDateService: ModelService() {

    private val dueDateDao = ModelDao.getInstance(DueDateDao::class.java)

    override fun selectById(id: Long): Any? = dueDateDao?.selectById(id)
    override fun selectAll(): List<DataModel>? = dueDateDao?.selectAll()
    override fun select(query: Query): List<DataModel>? = dueDateDao?.select(query)
    override fun insert(model: DataModel) { dueDateDao?.insert(model) }
    override fun update(model: DataModel) { dueDateDao?.update(model) }
    override fun delete(model: DataModel) { dueDateDao?.delete(model) }
}