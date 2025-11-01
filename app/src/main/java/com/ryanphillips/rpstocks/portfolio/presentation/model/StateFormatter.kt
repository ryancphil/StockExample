package com.ryanphillips.rpstocks.portfolio.presentation.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Int.formatCents(): String {
    val dollars = this / 100.0
    val formatted = String.format(
        Locale.getDefault(),
        "%.2f",
        dollars
    )
    return "$$formatted"
}

fun Int.formatTimestamp(): String {
    val milliseconds = this.toLong() * 1000 // Assuming timestamp is in seconds
    val date = Date(milliseconds)
    val formatter = SimpleDateFormat(
        "MMM d yyyy, h:mma",
        Locale.getDefault()
    )
    return formatter.format(date)
}