package com.mattrition.frutigertasks.extensions

import com.mattrition.frutigertasks.model.DEFAULT_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Capitalizes the first character of the string and sets the rest to lowercase.
 *
 * @return this string in sentence-cased format
 */
fun String.sentenceCase() = this.lowercase().replaceFirstChar { it.uppercase() }

/**
 * Reformats this date string.
 *
 * @param newFormat The new date format
 * @param currentFormat The current date format of the string
 * @param timeZone time zone to base the current string off of
 * @param offsetTimeZone time zone to adjust the new format off of
 * @return the newly formatted date string
 * @see java.text.SimpleDateFormat
 */
fun String.reformatDate(
    newFormat: String,
    currentFormat: String = DEFAULT_TIME_FORMAT,
    timeZone: TimeZone = TimeZone.getTimeZone("UTC"),
    offsetTimeZone: TimeZone = timeZone
): String {
    val fmt = SimpleDateFormat(currentFormat, Locale.getDefault())
    val newFmt = SimpleDateFormat(newFormat, Locale.getDefault())
    fmt.timeZone = timeZone
    newFmt.timeZone = offsetTimeZone

    return newFmt.format(fmt.parse(this)!!)
}

/**
 * Converts this date-formatted string to milliseconds since epoch time.
 *
 * @param currentFormat The date format of this string
 * @return amount of time since epoch
 * @see java.text.SimpleDateFormat
 */
fun String.fromDateToMillis(currentFormat: String = DEFAULT_TIME_FORMAT): Long {
    val formatter = SimpleDateFormat(currentFormat, Locale.getDefault())

    return formatter.parse(this)?.time ?: throw Exception("Invalid date format: $this")
}
