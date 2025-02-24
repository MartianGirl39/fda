package com.example.todolistapp.data

import android.content.Context
import android.database.Cursor
import com.example.todolistapp.model.TodoItem
import kotlin.reflect.KClass

private interface ModelDAOInterface<T> {
    fun selectById(id: Long): Any?
    fun selectAll(): List<T>?
    fun select(query: Query): List<T>?
    fun insert(model: T): Long?
    fun update(model: T)
    fun delete(model: T)
}

abstract class ModelDao(private val contextReference: Context): ModelDAOInterface<DataModel> {

    companion object {
        private val instances: MutableMap<Class<*>, ModelDao> = mutableMapOf()
        fun <T : ModelDao> getInstance(clazz: Class<T>, contextReference: Context): T = instances.getOrPut(clazz) { clazz.getConstructor(Context::class.java).newInstance(contextReference) } as T
        fun <T : ModelDao> getInstance(clazz: Class<T>): ModelDao? = instances[clazz]
    }

    private val sqlManager: LocalStorageManager? = LocalStorageManager.getInstance();
    abstract fun getTypeCopy(): KClass<out DataModel>
    abstract fun mapFromCursor(cursor: Cursor): DataModel

    override fun selectById(id: Long): DataModel? = sqlManager?.selectById(id, getTypeCopy())?.use { return if(it.moveToFirst()) mapFromCursor(it) else null}

    override fun selectAll(): List<DataModel>? {
        return sqlManager?.selectAll(getTypeCopy())?.use {
            val list: MutableList<DataModel> = mutableListOf<DataModel>()
            if(it.moveToFirst()) {
                do {
                    list.add(mapFromCursor(it))
                } while(it.moveToNext())
            }
            return list
        }
    }

    override fun select(query: Query): List<DataModel>? {
        query.setModel(getTypeCopy())
        return sqlManager?.select(query)?.use {
            val list: MutableList<DataModel> = mutableListOf<DataModel>()
            if(it.moveToFirst()) {
                do {
                    list.add(mapFromCursor(it))
                } while(it.moveToNext())
            }
            return list
        }
    }

    override fun insert(model: DataModel): Long? {
        return sqlManager?.insert(model)
    }

    override fun update(model: DataModel) {
        sqlManager?.update(model)
    }

    override fun delete(model: DataModel) {
        sqlManager?.delete(model)
    }
}
