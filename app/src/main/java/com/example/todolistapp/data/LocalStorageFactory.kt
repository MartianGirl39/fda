package com.example.todolistapp.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

class LocalStorageFactory private constructor(private val context: Context, private val dbName: String, private val version: Int, private val models: List<DataModel> = emptyList(), private val itemsToPopulate: List<DataModel>) {

    init {
        LocalStorageManager.initInstance(context, models, dbName, version)
        if (itemsToPopulate.size > 1) LocalStorageManager.populate(itemsToPopulate)
    }

    companion object {
        fun newBuilder(context: Context) = Builder(context)

        class Builder(private val context: Context) {
            private var dbName = "my_locally_stored_app"    // change this to be found out from xml
            private var version = 1
            private var models: List<DataModel> = emptyList()
            private var itemsToPopulate: List<DataModel> = emptyList()

            fun setDatabaseName(name: String) = this.apply { dbName = name }
            fun setVersion(version: Int) = this.apply { this.version = version }
            fun setModel(models: List<DataModel>) = this.apply { this.models = models }
            fun setPopulationScript(itemsToPopulate: List<DataModel>) = this.apply { this.itemsToPopulate = itemsToPopulate }

            fun build(): LocalStorageFactory = LocalStorageFactory(context, dbName, version, models, itemsToPopulate)
        }
    }

    internal fun getLocalStorage() = LocalStorageManager.getInstance()
}