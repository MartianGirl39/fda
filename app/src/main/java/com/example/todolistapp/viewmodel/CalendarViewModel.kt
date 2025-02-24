package com.example.todolistapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.Query
import com.example.todolistapp.data.service.TodoService
import com.example.todolistapp.model.DateItem
import com.example.todolistapp.model.TodoList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// calendar state view needs date and todos for each date
class CalendarViewModel(private val date: DateItem): ViewModel() {

    private val _calendarState = MutableStateFlow(CalenderViewState(date))
    val calendarState: MutableStateFlow<CalenderViewState> = _calendarState

    init {
        _calendarState.value = CalenderViewState(DateItem.today())
    }
    
    fun updateCalendarState(dateItem: DateItem) {
        _calendarState.value = CalenderViewState(dateItem)
    }

    class CalenderViewState(private val date: DateItem) {
        companion object {
            private val todoService = ModelService.getOrCreateInstance(TodoService::class.java)
        }

        private val today: DateItem = date
        private var dailyLists = todoService.selectByMonth(date)

        fun getLists() = dailyLists
        fun getDay() = today
        fun getMonth() = today.getMonth()
        fun getYear() = today.getYear()
    }
}