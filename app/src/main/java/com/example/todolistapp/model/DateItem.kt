package com.example.todolistapp.model

import java.util.Calendar

class DateItem private constructor(private val year: Int, private val month: Month, private val day: Int, private val week: Week) {
    class Month private constructor(private val month: MONTH) {
        private enum class MONTH(val monthName: String, val days: Int, val century: Int) {
            JANUARY("January", 31, 0),
            FEBRUARY("February", 28, 3),
            MARCH("March", 31, 3),
            APRIL("April", 30, 6),
            MAY("May", 31, 1),
            JUNE("June", 30, 4),
            JULY("July", 31, 6),
            AUGUST("August", 31, 2),
            SEPTEMBER("September", 30, 5),
            OCTOBER("October", 31, 0),
            NOVEMBER("November", 30, 3),
            DECEMBER("December", 31, 5);

            companion object {
                fun getMonth(month: Int): MONTH = entries[month - 1]
            }
        }


        companion object {
            fun newInstance(month: Int): Month {
                if (month > 12 || month < 1) throw IllegalArgumentException("value month must represent a valid month")
                return Month(MONTH.getMonth(month))
            }

            fun copy(month: Month): Month {
                return Month(MONTH.getMonth(month.getMonthNumber()))
            }
        }

        fun getMonthName() = month.name
        fun getMonthAbbrev() = month.name.subSequence(0, 3)
        fun getMonthNumber() = month.ordinal + 1
        fun getDaysInMonth() = month.days
        fun getCenturyNumber() = month.century
    }

    class Week private constructor(private val week: WEEK) {
        private enum class WEEK(week: String) {
            SUNDAY("Sunday"),
            MONDAY("Monday"),
            TUESDAY("Tuesday"),
            WEDNESDAY("Wednesday"),
            THURSDAY("Thursday"),
            FRIDAY("Friday"),
            SATURDAY("Saturday");

            companion object {
                fun getWeek(week: Int): WEEK = WEEK.entries[week - 1]
            }
        }

        companion object {
            fun newInstance(week: Int): Week {
                if (week < 1 || week > 7) throw IllegalArgumentException("value week must represent a valid week")
                return Week(WEEK.getWeek(week))
            }

            fun copy(week: Week): Week {
                return Week(WEEK.getWeek(week.getWeekNumber()))
            }
        }

        fun getWeekName() = week.name
        fun getWeekAbbrev() = week.name.subSequence(0, 3)
        fun getWeekNumber() = week.ordinal + 1
    }

    companion object {
        fun of(year: Int, month: Int, day: Int): DateItem {
            if (year.toString().length != 4) throw IllegalArgumentException("year must be 4 digits long")
            val monthValue = Month.newInstance(month)
            if (day > monthValue.getDaysInMonth()) throw IllegalArgumentException("day must be a valid day of month")
            val week = Week.newInstance(calcWeekDay(year, monthValue, day))
            return DateItem(year, monthValue, day, week)
        }

        fun today(): DateItem {
            val calendar = Calendar.getInstance()
            return this.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }

        private fun calcWeekDay(year: Int, month: Month, day: Int): Int {
            val yearCode = getYearCode(year)
            val centuryCode = getCenturyCode(year)
            val leapCode = if (year % 4 == 0) 1 else 0
            return (yearCode + month.getCenturyNumber() + centuryCode + day - leapCode) % 7
        }

        private fun getCenturyCode(year: Int): Int {
            return 6
        }

        private fun getYearCode(year: Int): Int {
            val yearStr = year.toString()
            val yy = yearStr.substring(2).toInt()
            return (yy + (yy / 4)) % 7

        }
    }

    fun getDayOfMonth() = day
    fun getYear() = year
    fun getMonth() = Month.copy(month)
    fun getWeek() = Week.copy(week)
}
