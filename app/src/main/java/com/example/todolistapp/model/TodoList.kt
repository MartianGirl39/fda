package com.example.todolistapp.model

class TodoList (private val name: String, private val description: String, private var todos: List<TodoItem>, private var orderingStrategy: OrderStrategy<TodoItem> = OrderByTime()){
    companion object {
        fun newInstance(date: DateItem, todos: List<TodoItem>): TodoList {
            return TodoList(date.toString(), "todos for ${date.toString()}", todos)
        }
    }

    fun getName() = name
    fun getDescription() = description
    fun getIterator() = ListIterator<TodoItem>(todos)
    fun setOrderingStrategy(strategy: OrderStrategy<TodoItem>) {
        this.orderingStrategy = strategy
        todos = orderingStrategy.order(todos)
    }
}