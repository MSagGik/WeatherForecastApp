package io.github.msaggik.weatherforecastapp.utils

import android.util.TimeFormatException

object Utils

fun formatDate(date: String): String {
    return try {
        val input = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val output = java.text.SimpleDateFormat("d MMM", java.util.Locale.forLanguageTag("ru"))
        output.format(input.parse(date)!!)
    } catch (e: TimeFormatException) {
        e.stackTrace.toString()
    }
}
