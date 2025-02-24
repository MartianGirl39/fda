package com.example.todolistapp.data.service

import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.TagDao

class TagService: ModelService() {

    private val tagDao = ModelDao.getInstance(TagDao::class.java)

    override fun selectById(id: Long): Any? = tagDao?.selectById(id)
    override fun selectAll(): List<DataModel>? = tagDao?.selectAll()
    override fun select(query: Query): List<DataModel>? = tagDao?.select(query)
    override fun insert(model: DataModel) { tagDao?.insert(model) }
    override fun update(model: DataModel) { tagDao?.update(model) }
    override fun delete(model: DataModel) { tagDao?.delete(model) }
}