/*
 * *  * Created by Don Muthiani on 6/3/20 11:11 AM  * Copyright (c) 2020. All rights reserved.  * Last modified 6/3/20 11:11 AM
 */

package com.bossco.spacexclient.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateFormatter private constructor() {
    init {
        throw AssertionError()
    }

    /**
     * Interface used to format dates before they were displayed (e.g. dialogs time, messages date headers etc.).
     */
    interface Formatter {
        /**
         * Formats a string representation of the date object.
         *
         * @param date The date that should be formatted.
         * @return Formatted text.
         */
        fun format(date: Date): String
    }

    enum class Template(private val template: String) {
        STRING_DAY_MONTH_YEAR("d MMMM yyyy"),
        STRING_DAY_MONTH_YEAR_TIME("d MMMM yyyy:HH:mm"),
        STRING_DAY_MONTH("d MMMM"),
        TIME("HH:mm");

        fun get(): String {
            return template
        }
    }

    companion object {

        fun format(date: Date, template: Template): String {
            return format(date, template.get())
        }

        fun format(date: Date?, format: String): String {
            return if (date == null) "" else SimpleDateFormat(format, Locale.getDefault())
                .format(date)
        }

        fun isSameDay(date1: Date?, date2: Date?): Boolean {
            require(!(date1 == null || date2 == null)) { "Dates must not be null" }
            val cal1 = Calendar.getInstance()
            cal1.time = date1
            val cal2 = Calendar.getInstance()
            cal2.time = date2
            return isSameDay(cal1, cal2)
        }

        fun isSameDay(cal1: Calendar?, cal2: Calendar?): Boolean {
            require(!(cal1 == null || cal2 == null)) { "Dates must not be null" }
            return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
        }

        fun isSameYear(date1: Date?, date2: Date?): Boolean {
            require(!(date1 == null || date2 == null)) { "Dates must not be null" }
            val cal1 = Calendar.getInstance()
            cal1.time = date1
            val cal2 = Calendar.getInstance()
            cal2.time = date2
            return isSameYear(cal1, cal2)
        }

        fun isSameYear(cal1: Calendar?, cal2: Calendar?): Boolean {
            require(!(cal1 == null || cal2 == null)) { "Dates must not be null" }
            return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(
                Calendar.YEAR
            )
        }

        fun isToday(calendar: Calendar): Boolean {
            return isSameDay(calendar, Calendar.getInstance())
        }

        fun isToday(date: Date): Boolean {
            return isSameDay(date, Calendar.getInstance().time)
        }

        fun isYesterday(calendar: Calendar): Boolean {
            val yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DAY_OF_MONTH, -1)
            return isSameDay(calendar, yesterday)
        }

        fun isYesterday(date: Date): Boolean {
            val yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DAY_OF_MONTH, -1)
            return isSameDay(date, yesterday.time)
        }

        fun isCurrentYear(date: Date): Boolean {
            return isSameYear(date, Calendar.getInstance().time)
        }

        fun isCurrentYear(calendar: Calendar): Boolean {
            return isSameYear(calendar, Calendar.getInstance())
        }
    }
}