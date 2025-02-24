package com.example.todolistapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolistapp.data.ModelService
import com.example.todolistapp.data.service.TodoService
import com.example.todolistapp.model.DateItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodayViewModel {

    private val _todayState = MutableStateFlow(TodayViewState(DateItem.today()))
    val todayState: StateFlow<TodayViewState> = _todayState

    fun updateCalendarState(dateItem: DateItem) {
        _todayState.value = TodayViewState(dateItem)
    }

    class TodayViewState(private val date: DateItem) {
        companion object {
            private val todoService = ModelService.getOrCreateInstance(TodoService::class.java)
        }

        private val today: DateItem = date
        private var dailyLists = todoService.selectByDay(date)

        fun getTodosForToday() = dailyLists
        fun getDay() = today
        fun getMonth() = today.getMonth()
        fun getYear() = today.getYear()
    }
}
