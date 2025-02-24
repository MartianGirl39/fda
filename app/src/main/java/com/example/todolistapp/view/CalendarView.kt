package com.example.todolistapp.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.todolistapp.model.DateItem
import com.example.todolistapp.viewmodel.CalendarViewModel

@Composable
fun CalendarView(date: DateItem) {
    val calViewModel = CalendarViewModel(date)
    val state = calViewModel.calendarState.collectAsState()


}