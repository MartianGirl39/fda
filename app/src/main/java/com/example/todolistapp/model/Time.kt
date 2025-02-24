package com.example.todolistapp.model

import android.database.Cursor
import com.example.todolistapp.data.DataModel
import com.example.todolistapp.data.Id
import java.time.LocalDateTime
import java.util.Calendar

class Time(private val hour: Int, private val minute: Int, private val timeOfDay: TIME_OF_DAY, @Id()private val id: Long = -1): DataModel {
    companion object {
        enum class TIME_OF_DAY {
            AM,
            PM
        }

        fun now(): Time {
            val time = Calendar.getInstance()
            return Time(time.get(Calendar.HOUR), time.get(Calendar.MINUTE), if(time.get(Calendar.AM_PM) == Calendar.AM) TIME_OF_DAY.AM else TIME_OF_DAY.PM)
        }
    }
    fun getHour() = hour
    fun getMinute() = minute
    private fun getMilitaryHour() = if(timeOfDay == TIME_OF_DAY.PM) hour + 12 else hour
    fun getMilitaryTime() = "${getMilitaryHour()}:$minute"
    override fun toString(): String {
        return "$hour:$minute$timeOfDay"
    }
    fun getTime(): Int = hour * 60 + minute
}