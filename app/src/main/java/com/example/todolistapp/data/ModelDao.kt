package com.example.todolistapp.data

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.model.TodoItem
import kotlin.reflect.KClass


// Modify the interface to be generic
private interface ModelDAOInterface<T : DataModel> {
    fun selectById(id: Long): T?
    fun selectAll(): List<T>?
    fun select(query: Query): List<T>?
    fun insert(model: T): Long?
    fun update(model: T)
    fun delete(model: T)
}

abstract class ModelDao<T : DataModel>(private val contextReference: Context) : ModelDAOInterface<T> {

    companion object {
        private val instances: MutableMap<Class<*>, ModelDao<*>> = mutableMapOf()
        fun <T : ModelDao<*>> getInstance(clazz: Class<T>, contextReference: Context): T =
            instances.getOrPut(clazz) { clazz.getConstructor(Context::class.java).newInstance(contextReference) } as T
        fun <T : ModelDao<*>> getInstance(clazz: Class<T>): ModelDao<*>? = instances[clazz]
    }
    private val sqlManager: LocalStorageManager? = LocalStorageManager.getInstance()

    abstract fun getTypeCopy(): KClass<out T>
    abstract fun mapFromCursor(cursor: Cursor): T

    override fun selectById(id: Long): T? = sqlManager?.selectById(id, getTypeCopy())?.use {
        return if (it.moveToFirst()) mapFromCursor(it) else null
    }

    override fun selectAll(): List<T>? {
        return sqlManager?.selectAll(getTypeCopy())?.use {
            val list: MutableList<T> = mutableListOf()
            if (it.moveToFirst()) {
                do {
                    list.add(mapFromCursor(it))
                } while (it.moveToNext())
            }
            return list
        }
    }

    override fun select(query: Query): List<T>? {
        query.setModel(getTypeCopy())
        return sqlManager?.select(query)?.use {
            val list: MutableList<T> = mutableListOf()
            if (it.moveToFirst()) {
                do {
                    list.add(mapFromCursor(it))
                } while (it.moveToNext())
            }
            return list
        }
    }

    override fun insert(model: T): Long? {
        return sqlManager?.insert(model)
    }

    override fun update(model: T) {
        sqlManager?.update(model)
    }

    override fun delete(model: T) {
        sqlManager?.delete(model)
    }
}
