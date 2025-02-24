package com.example.todolistapp.data

import kotlin.reflect.KClass

class Query private constructor(private var model: KClass<out DataModel>, private val filtering: String?, private val args: Array<String>?, private val offset: Int?, private val limit: Int?, private val orderBy: String?, private val groupBy: String?, private val orderDirection: ORDER_DIRECTION?) {

    companion object  {
        enum class ORDER_DIRECTION(value:String) {
            ASCENDING("ASCENDING"),
            DESCENDING("DESCENDING")
        }

        fun newQueryBuilder(model: KClass<out DataModel>) = Builder(model)

        class Builder(private val model: KClass<out DataModel>) {
            private val unsafeCharsRegex = "[;'\"\\-\\(\\)\\&\\|\\=\\<\\>\\%\\#\\*\\+(SELECT|DROP|INSERT|DELETE|UPDATE|CREATE|ALTER|TRUNCATE|EXEC)]".toRegex()
            private var filtering: String? = null;
            private var args: Array<String>? = null
            private var offset: Int? = null;
            private val limit: Int? = null;
            private var orderBy: String? = null;
            private var groupBy: String? = null
            private var orderDirection: ORDER_DIRECTION? = null

            fun setFilter(filtering: String): Builder { this.filtering = filtering; return this; }
            fun setFilteringArgs(args: Array<String>): Builder {
                this.args = args.map { it.replace(unsafeCharsRegex, "") }.toTypedArray()
                return this
            }
            fun setOffset(offset: Int): Builder { this.offset = offset; return this; }
            fun setOrderBy(orderBy: String): Builder { this.orderBy = orderBy; return this; }
            fun setGroupBy(groupBy: String): Builder { this.groupBy = groupBy; return this; }
            fun setOrderingDirection(direction: ORDER_DIRECTION): Builder { this.orderDirection = direction; return this; }

            fun build(): Query {
                return Query(model, filtering, args, offset, limit, orderBy, groupBy, orderDirection)
            }
        }
    }
    fun getFilter() = filtering
    fun getFilteringArgs() = args
    fun getGroupBy() = groupBy
    fun getModel() = model
    fun getOrdering() = if(orderBy == null) null else orderBy + (orderDirection ?: "")
    fun getLimiting(): String? = if(limit == null) null else "$limit" + ("OFFSET $offset")
    fun setModel(typeCopy: KClass<out DataModel>) {
        this.model = typeCopy
    }
}