package com.example.todolistapp.data.service

import com.example.todolistapp.data.ModelDao
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.dao.TagDao
import com.example.todolistapp.model.Tag

class TagService: ModelService<Tag>() {

    private val tagDao = ModelDao.getInstance(TagDao::class.java)

    override fun selectById(id: Long): Any? = tagDao.selectById(id)
    override fun selectAll(): List<Tag>? = tagDao.selectAll()
    override fun select(query: Query): List<Tag> = tagDao.select(query)
    override fun insert(model: Tag) { tagDao.insert(model) }
    override fun update(model: Tag) { tagDao.update(model) }
    override fun delete(model: Tag) { tagDao.delete(model) }
}