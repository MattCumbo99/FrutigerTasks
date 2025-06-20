package com.mattrition.frutigertasks.extensions

import com.mattrition.frutigertasks.model.DEFAULT_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Converts this [Long] to a date-formatted string. This method assumes the number is an actual
 * amount of time elapsed since epoch time in milliseconds.
 *
 * @param format How the date should be formatted.
 * @param timeZone The timezone to format the result off of.
 * @return the formatted date-time string.
 * @see java.text.SimpleDateFormat
 */
fun Long.asDate(
    format: String = DEFAULT_TIME_FORMAT,
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    formatter.timeZone = timeZone

    return formatter.format(Date(this))
}
