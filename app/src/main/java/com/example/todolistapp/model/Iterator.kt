package com.example.todolistapp.model

interface Iterator<T> {
    fun next(): Boolean
    fun previous(): Boolean
    fun first(): Boolean
    fun last(): Boolean
    fun get(): T?
}

class ListIterator<T>(private val list: List<T>): Iterator<T> {
    private var index = 0
    override fun next(): Boolean {
        index += 1
        return index > list.size
    }

    override fun previous(): Boolean {
        index -=1
        return index < 0
    }

    override fun first(): Boolean {
        index = 0
        return list.isNotEmpty()
    }

    override fun last(): Boolean {
        index = list.size
        return list.isNotEmpty()
    }

    override fun get(): T? {
        return if(index < list.size && index > 0) list[index] else null
    }
}