package com.mattrition.frutigertasks.extensions

import java.util.Calendar

/** Removes information related to time from the calendar. */
fun Calendar.removeTime() {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
}
