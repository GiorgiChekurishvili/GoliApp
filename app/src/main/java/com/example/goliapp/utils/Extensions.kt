package com.example.goliapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String?.orDash(): String = if (isNullOrBlank()) "-" else this

fun String.toReadableDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()
        val date: Date = inputFormat.parse(this) ?: return this
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun String.toMatchTime(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()
        val date: Date = inputFormat.parse(this) ?: return this
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun String.toShortDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        val date: Date = inputFormat.parse(this) ?: return this
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun todayDateString(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(Date())
}
