package com.mattrition.frutigertasks.model.scheduler

import android.os.Build
import androidx.annotation.RequiresApi
import com.mattrition.frutigertasks.extensions.asDate
import com.mattrition.frutigertasks.extensions.fromDateToMillis
import com.mattrition.frutigertasks.extensions.removeTime
import com.mattrition.frutigertasks.extensions.sentenceCase
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Represents a timer to trigger an object.
 *
 * @property startDate Starting date of the schedule formatted as `MM/dd/yyyy HH:mm:ss z`.
 * @property dailyRepeat The amount of days until the schedule should repeat.
 * @property onDaysOfWeek A set containing weekdays that this schedule should trigger on.
 * @property onDayOfMonth Integer value containing the day of the month this schedule should trigger
 *   on. The first day of each month is represented by `1`.
 * @property onDaysOfYear A set of dates containing days of the year this schedule should trigger
 *   on.
 * @property endDate Date to stop the schedule at.
 * @property completed If this schedule has been marked as complete.
 */
class Schedule(
    var startDate: String = Date().time.asDate(timeZone = TimeZone.getDefault()),
    var dailyRepeat: Int? = null,
    var onDaysOfWeek: Set<DayOfWeek> = emptySet(),
    var onDayOfMonth: Int? = null,
    var onDaysOfYear: Set<String> = emptySet(),
    var endDate: String? = null,
    var completed: Boolean = false
) {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val WEEKDAYS =
            setOf(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
            )

        @RequiresApi(Build.VERSION_CODES.O)
        val WEEKENDS = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
    }

    /**
     * Checks if this schedule is active on the specified date (or today).
     *
     * @param onDate Date to check the schedule against.
     * @param againstTimeZone The timezone to check the date against
     * @return true if the schedule would trigger on the date.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun isActive(
        onDate: String = Date().time.asDate(timeZone = TimeZone.getDefault()),
        againstTimeZone: TimeZone = TimeZone.getDefault()
    ): Boolean {
        val startCalendar = Calendar.getInstance(againstTimeZone)
        startCalendar.time = Date(startDate.fromDateToMillis())
        startCalendar.removeTime()

        val currentCalendar = Calendar.getInstance(againstTimeZone)
        currentCalendar.time = Date(onDate.fromDateToMillis())
        currentCalendar.removeTime()

        val hasNotEnded =
            endDate?.let {
                val endCalendar = Calendar.getInstance(againstTimeZone)
                endCalendar.time = Date(it.fromDateToMillis())
                endCalendar.removeTime()

                // Check if the date is still within this schedule's end date
                endCalendar.timeInMillis >= currentCalendar.timeInMillis
            } == true || endDate == null

        val daysOfYear =
            onDaysOfYear.map { time ->
                val calendar = Calendar.getInstance(againstTimeZone)
                calendar.time = Date(time.fromDateToMillis())
                // Set each date to the current date's year
                calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR))
                calendar.removeTime()

                calendar
            }

        // Conditionals
        val isInitialDay = startCalendar.compareTo(currentCalendar) == 0
        val hasStarted = startCalendar < currentCalendar
        val isOnDayOfYear = daysOfYear.any { it.compareTo(currentCalendar) == 0 }

        val daysBetween =
            ChronoUnit.DAYS.between(startCalendar.toInstant(), currentCalendar.toInstant())
        val isOnRepeatDay = dailyRepeat?.let { daysBetween % it.toLong() == 0L } == true

        val currentDayOfWeek = currentCalendar.get(Calendar.DAY_OF_WEEK).adjustedDay()
        val currentDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH)

        return hasNotEnded &&
            !completed &&
            hasStarted &&
            (
                isInitialDay ||
                    currentDayOfWeek in onDaysOfWeek ||
                    currentDayOfMonth == onDayOfMonth ||
                    isOnDayOfYear ||
                    isOnRepeatDay
                )
    }

    /**
     * Shows when this schedule activates as a string.
     *
     * Examples:
     * 1. _Repeats on Saturdays_
     * 2. _Repeats daily_
     * 3. _Repeats every year on Jun 6_
     *
     * @return String version of this schedule
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun asRepeatString(): String {
        val repeatParams = mutableListOf<String>()
        dailyRepeat?.let { days ->
            repeatParams.add(
                when (days) {
                    1 -> "daily"
                    7 -> {
                        val tempCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                        tempCal.time = Date(startDate.fromDateToMillis())
                        tempCal.removeTime()

                        val day = tempCal.get(Calendar.DAY_OF_WEEK).adjustedDay()

                        "on ${day.name.sentenceCase()}s"
                    }
                    else -> "every $days days"
                }
            )
        }

        if (onDaysOfWeek.isNotEmpty()) {
            val daysInWeek =
                when (onDaysOfWeek) {
                    WEEKENDS -> "weekends"
                    WEEKDAYS -> "weekdays"
                    else -> {
                        onDaysOfWeek.joinToString(", ") { dayEnum ->
                            "${dayEnum.name}s".sentenceCase()
                        }
                    }
                }

            repeatParams.add("on $daysInWeek")
        }

        onDayOfMonth?.let { repeatParams.add("on day $it of the month") }

        if (onDaysOfYear.isNotEmpty()) {
            val yearDays =
                onDaysOfYear.joinToString("; ") {
                    val formatter = SimpleDateFormat("MMM d", Locale.getDefault())
                    formatter.format(Date(it.fromDateToMillis()))
                }

            repeatParams.add("yearly on $yearDays")
        }

        return if (repeatParams.isEmpty()) {
            "Does not repeat"
        } else {
            "Repeats ${repeatParams.joinToString(", ")}"
        }
    }
}

/**
 * Function for adjusting the numerical representation of a [Calendar] integer to correctly map it
 * to a [DayOfWeek] enum.
 */
@RequiresApi(Build.VERSION_CODES.O)
private fun Int.adjustedDay() = when (this) {
    Calendar.SUNDAY -> DayOfWeek.SUNDAY
    else -> DayOfWeek.of(this - 1)
}
