package com.example.todolistapp.model

class DueDate(private val dateItem: DateItem, private val time: Time) {
    fun getDate() = dateItem
    fun getTime() = time
}