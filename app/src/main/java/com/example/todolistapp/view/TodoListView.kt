package com.example.todolistapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolistapp.data.service.TodoService
import com.example.todolistapp.model.TodoItem

@Composable
fun TodoListView(todos: List<TodoItem>) {
    val unfinishedTodo = remember { todos.filter { !it.isFinished() } }
    val finished = remember { todos.filter { it.isFinished() } }
    val scroll = rememberScrollState()

    if (todos.isEmpty()) EmptyList()
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Card() {
            Column(Modifier.verticalScroll(scroll)) {
                Column {
                    Text("Todo")
                    unfinishedTodo.forEach() { Todo(it) }
                    Spacer(Modifier.fillMaxWidth())
                    Text("Completed")
                    finished.forEach() { Todo(it) }
                }
            }
        }
    }
}

@Composable
fun Todo(todoItem: TodoItem) {
    val service = remember { TodoService() }
    Text(todoItem.getName())
    Row {
        Text(todoItem.getDescription())
        Checkbox(todoItem.isFinished(), {service.update(todoItem.toggleFinished())})
    }
}

@Composable
fun EmptyList() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        Card() {    // fill width 75%
            Text("Rest up! You have nothing to do today")
        }
    }
}

@Composable
fun FinishedList(todos: List<TodoItem>) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Card() {
            Text("Congrats! You have left to do today")
        }
    }
}