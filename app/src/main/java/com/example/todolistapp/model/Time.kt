package com.example.todolistapp.model

import java.time.LocalDateTime
import java.util.Calendar

class Time(private val hour: Int, private val minute: Int, private val timeOfDay: TimeOfDay) {
    companion object {
        enum class TimeOfDay {
            AM,
            PM
        }

        fun now(): Time {
            val time = Calendar.getInstance()
            return Time(time.get(Calendar.HOUR), time.get(Calendar.MINUTE), if(time.get(Calendar.AM_PM) == Calendar.AM) TimeOfDay.AM else TimeOfDay.PM)
        }
    }
    fun getHour() = hour
    fun getMinute() = minute
    private fun getMilitaryHour() = if(timeOfDay == TimeOfDay.PM) hour + 12 else hour
    fun getMilitaryTime() = "${getMilitaryHour()}:$minute"

    override fun toString(): String {
        return "$hour:$minute$timeOfDay"
    }

    fun getTime(): Int = hour * 60 + minute
}