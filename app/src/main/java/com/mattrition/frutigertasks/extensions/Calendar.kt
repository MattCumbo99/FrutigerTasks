package com.mattrition.frutigertasks.extensions

import java.util.Calendar

/** Sets the time to 00:00:00 (12:00am). */
fun Calendar.removeTime() {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
}
