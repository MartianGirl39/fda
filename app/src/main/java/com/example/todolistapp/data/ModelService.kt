package com.example.todolistapp.data

import android.content.Context

abstract class ModelService<T: DataModel> {

    companion object {
        // This will store the singleton instances for each subclass
        private val instances: MutableMap<Class<*>, ModelService<*>> = mutableMapOf()

        // Generic function to get the singleton instance
        fun <T : ModelService<*>> getOrCreateInstance(clazz: Class<T>): T {
            return instances.getOrPut(clazz) {
                clazz.getConstructor().newInstance()
            } as T
        }
    }

    abstract fun selectById(id: Long): Any?
    abstract fun selectAll(): List<T>?
    abstract fun select(query: Query): List<T>?
    abstract fun insert(model: T)
    abstract fun update(model: T)
    abstract fun delete(model: T)
}