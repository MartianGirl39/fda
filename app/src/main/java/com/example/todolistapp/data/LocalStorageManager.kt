package com.example.todolistapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.app.NotificationCompat.StreamType
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.reflect.KClass

internal class LocalStorageManager private constructor(context: Context?, models: List<DataModel>, dbName: String, version: Int): SQLiteOpenHelper(context, dbName, null, version) {

    companion object {
        // single instance reference
        @Volatile
        private var INSTANCE: LocalStorageManager? = null
        fun initInstance(context: Context?, models: List<DataModel>, dbName: String, version: Int): LocalStorageManager = INSTANCE ?: synchronized(this) { INSTANCE ?: LocalStorageManager(context, models, "$dbName.db", version).also { INSTANCE = it } }
        fun getInstance(): LocalStorageManager? {
            return INSTANCE
        }
        fun populate(itemsToPopulate: List<DataModel>) {
            itemsToPopulate.sortedBy { it::class.simpleName }
            INSTANCE?.populate(itemsToPopulate)
        }
    }
    private fun populate(itemsToPopulate: List<DataModel>) {
        val db = writableDatabase
        var index = 0
        var currentTable = modelMapping[itemsToPopulate[index]::class]?.getTable() ?: ""
        val populationScript = StringBuilder("BEGIN TRANSACTION")
        while (index < itemsToPopulate.size) {
            if (isTableEmpty(currentTable)) {
                populationScript.append("; INSERT INTO $currentTable VALUES ")
                while (index < itemsToPopulate.size && modelMapping[itemsToPopulate[index]::class]?.getTable() == currentTable) {
                    populationScript.append(
                        "(${
                            modelMapping[itemsToPopulate[index]::class]?.extractData(
                                itemsToPopulate[index]
                            )?.joinToString(", ")
                        })"
                    )
                    index += 1
                    // Update currentTable for the next item in the list, if needed
                    if (index < itemsToPopulate.size) currentTable =
                        modelMapping[itemsToPopulate[index]::class]?.getTable() ?: ""
                    if (index < itemsToPopulate.size && modelMapping[itemsToPopulate[index]::class]?.getTable() == currentTable) populationScript.append(
                        ", "
                    )
                }
            }
        }
        populationScript.append("; END TRANSACTION;")
        db.execSQL(populationScript.toString())
    }

    private fun isTableEmpty(tableName: String): Boolean {
        val db = readableDatabase
        val query = "SELECT COUNT(*) AS count FROM $tableName";
        val cursor = db.rawQuery(query, null);
        var isEmpty = true
        cursor.use {
            if (cursor.moveToFirst()) {
                val rowCount = cursor.getInt(cursor.getColumnIndexOrThrow("count")); // Get the count of rows
                isEmpty = rowCount == 0;
            }
        }
        return isEmpty
    }


    private val unsafeCharsRegex = "[;'\"\\-\\(\\)\\&\\|\\=\\<\\>\\%\\#\\*\\+(SELECT|DROP|INSERT|DELETE|UPDATE|CREATE|ALTER|TRUNCATE|EXEC)]".toRegex()

    private class ModelMapping() {
        private var table: String = ""
        private var columns: Map<String, Class<*>> = HashMap()
        private var id: Field?
        private lateinit var accessibleFields: List<Field>

        private fun findType(field: Field): Class<*> {
            return if(field.type.isInstance(DataModel::class.java)) Int::class.java
            else if(field.type.isInstance(Enum)) String::class.java
            else field.type
        }

        constructor(model: DataModel) : this() {
            val prototype = model::class.java
            table = prototype.getAnnotation(Table::class.java)?.tableName ?: prototype.simpleName
            accessibleFields = prototype.declaredFields.filterNot { it.isAnnotationPresent(Id::class.java)}.map { field ->
                field.isAccessible = true
                field
            }
        }

        init {
            val idField = accessibleFields.find { field -> field.isAnnotationPresent(Id::class.java) }
            id = idField
            columns = accessibleFields
                .filterNot { it.isAnnotationPresent(Id::class.java) }
                .associate { field ->
                    field.name to findType(field)
                }
        }

        fun getTable() = table
        fun getColumnInfo() = columns
        fun getColumns() = accessibleFields.map { it.name }
        fun getId() = id?.name ?: ""
        fun getFields() = accessibleFields

        // Extract data from the DataModel, using pre-cached accessible fields
        fun extractData(model: DataModel): List<Any?> {
            return accessibleFields
                .filter { !it.isAnnotationPresent(Id::class.java) }
                .map { it.get(model) }
        }

        // Extract ID using pre-cached accessible fields
        fun extractID(model: DataModel): String = accessibleFields.find { it.isAnnotationPresent(Id::class.java) }?.get(model).toString()
    }

    private val modelMapping = models.associate { it::class to ModelMapping(it) }

    private fun parseJoinCauses(model: KClass<out DataModel>): String? {
        val table = modelMapping[model]?.getTable()
        return modelMapping[model]?.getFields()
            ?.filter { it.isAnnotationPresent(JOIN::class.java) && it.type.isInstance(DataModel::class.java) }
            ?.joinToString(" ") { prop ->
                val joinAnnotation = prop.getAnnotation(JOIN::class.java)
                val innerMapping = modelMapping[joinAnnotation?.onModel]
                "JOIN ${innerMapping?.getTable()} ON ${table}.${prop.name} = ${innerMapping?.getTable()}.${joinAnnotation?.onValue}" + joinAnnotation?.onModel?.let { " " + parseJoinCauses(it)
                }
            }
    }

    private fun parseQuery(query: Query): String {
        val model = query.getModel()
        val table = modelMapping[model]?.getTable()
        val joinClauses = parseJoinCauses(model)
        return """
        SELECT * FROM $table
            $joinClauses
            ${if(query.getFilter() != null) "WHERE ${query.getFilter()}" else ""}
            ${if(query.getGroupBy() != null) "GROUP BY ${query.getGroupBy()}" else ""}
            ${if(query.getOrdering() != null) "ORDER BY ${query.getOrdering()}" else ""}
            ${if(query.getLimiting() != null) "LIMIT ${query.getLimiting()}" else ""}
        """.trimIndent()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val database = writableDatabase
        this.modelMapping.forEach {
            val mapping = it.value
            val query = """CREATE TABLE ${mapping.getTable()} (
                ${mapping.getId()} INTEGER NOT NULL AUTOINCREMENT
                ${
                mapping.getColumnInfo().map { field -> "${field.key} ${field.value.simpleName}" }
                    .joinToString(", ")
            }
                );
            """.trimMargin()
            database.execSQL(query)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
        // drop all tables for now
    }

    fun selectById(id: Long, model: KClass<out DataModel>): Cursor {
        val database = readableDatabase
        val cursor = database.rawQuery(parseQuery(Query.newQueryBuilder(model)
            .setFilter("id = ?")
            .build()
        ), arrayOf((id.toString())))
        return cursor
//        val result = cursor.use { if (it.moveToFirst()) modelMapping[model]?.mapFromCursor(cursor) }  // for now, but there might be better strategy
//        return result
    }

    fun selectAll(model: KClass<out DataModel>): Cursor {
        val database = readableDatabase
        val cursor = database.rawQuery(parseQuery(Query.newQueryBuilder(model).build()), arrayOf())
        return cursor
//        val result = mutableListOf<DataModel>()
//        cursor.use {
//            if (it.moveToFirst()) do {
//                result.add(model.mapFromCursor(cursor))
//            } while(it.moveToNext())
//        }
//        return result
    }

    fun select(queryParam: Query): Cursor {
        val database = readableDatabase
        val cursor = database.rawQuery(parseQuery(queryParam), queryParam.getFilteringArgs())
        return cursor
//        val result = mutableListOf<DataModel>()
//        cursor.use { if(it.moveToFirst()) do {
//            result.add(modelMapping[queryParam.getModel()]?.mapFromCursor(cursor) as DataModel)
//            } while (it.moveToNext())
//        }
//        return result
    }

    fun insert(model: DataModel): Long {
        val database = writableDatabase
        val table = modelMapping[model::class]?.getTable()
        val data = modelMapping[model::class]?.extractData(model)
        val columns = modelMapping[model::class]?.getColumns()
        val contentValues = ContentValues()
        var i = 0
        while(i < data?.size!! && i < columns?.size!!) {
            contentValues.put(columns[i], data[i].toString().replace(unsafeCharsRegex, ""))
            i++
        }
        return database.insert(table, null, contentValues)
    }

    fun update(model: DataModel) {
        val table = modelMapping[model::class]?.getTable()
        val data = modelMapping[model::class]?.extractData(model)
        val columns = modelMapping[model::class]?.getColumns()
        val contentValues = ContentValues()
        var i = 0
        while(i < data?.size!! && i < columns?.size!!) {
            contentValues.put(columns[i], data[i].toString().replace(unsafeCharsRegex, ""))
            i++
        }
        writableDatabase.update(table, contentValues, "${modelMapping[model::class]?.getId()}= ?", arrayOf(modelMapping[model::class]?.extractID(model).toString()))
    }

    fun delete(model: DataModel) {
        val database = writableDatabase
        val table = modelMapping[model::class]?.getTable()
        database.delete(table, "${modelMapping[model::class]?.getId()}= ?", arrayOf(modelMapping[model::class]?.extractID(model).toString()))
    }
}
