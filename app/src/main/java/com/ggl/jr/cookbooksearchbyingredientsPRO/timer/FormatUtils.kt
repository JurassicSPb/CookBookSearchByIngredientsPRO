package com.ggl.jr.cookbooksearchbyingredientsPRO.timer

import java.text.SimpleDateFormat
import java.util.*

class FormatUtils private constructor() {
    private val simpleDateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())


    fun formattedTime(hourOfDay: Long, minute: Long, seconds: Long): String {
        try {
            val date = simpleDateFormat.parse("$hourOfDay:$minute:$seconds")
            return simpleDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return DEFAULT_VALUE
    }


    private object Holder {
        val INSTANCE = FormatUtils()
    }

    companion object {
        val instance: FormatUtils by lazy { Holder.INSTANCE }
        private const val timeFormat = "HH:mm:ss"
        private const val DEFAULT_VALUE = ""
    }
}
