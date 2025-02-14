package com.example.todolistapp.model

interface OrderStrategy<T> {
    fun order(list: List<T>): List<T>
}

class OrderByName: OrderStrategy<TodoItem> {
    override fun order(list: List<TodoItem>): List<TodoItem> {
        // sort list items by time and then priority
        val newList = list.sortedBy { it.getName() }
        return listOf()
    }
}

class OrderByTime: OrderStrategy<TodoItem> {
    override fun order(list: List<TodoItem>): List<TodoItem> {
        // sort list items by time and then priority
        val newList = list.sortedBy {it.getDueDate().getTime().getTime()}
        return newList
    }
}

class OrderByPriority: OrderStrategy<TodoItem> {
    override fun order(list: List<TodoItem>): List<TodoItem> {
        // sort list items by time and then priority
        val newList = list.sortedBy {it.getUrgency().ordinal + it.getImportance().ordinal}.reversed()
        return newList
    }
}