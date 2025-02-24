package com.example.todolistapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.todolistapp.model.TodoItem
import com.example.todolistapp.model.TodoList
import com.example.todolistapp.viewmodel.TodayViewModel

@Composable
fun Today() {

    val todoViewModel = remember { TodayViewModel() }
    val todoState by todoViewModel.todayState.collectAsState()

    Column {
        TodoListView(todoState.getTodosForToday())
    }
}