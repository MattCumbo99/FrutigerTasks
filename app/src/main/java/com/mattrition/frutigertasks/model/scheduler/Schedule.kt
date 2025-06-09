package com.mattrition.frutigertasks.model.scheduler

import java.util.Date

/**
 * Represents a timer to trigger an object.
 *
 * @param startDate The date to start the schedule on.
 * @param reminders When to remind the user about the schedule.
 * @param repeatEvery When the schedule should repeat.
 * @param endDate The date to stop the schedule on.
 */
data class Schedule(
    val startDate: Date = Date(),
    val reminders: MutableSet<TimeType> = mutableSetOf(),
    val repeatEvery: TimeType? = null,
    val endDate: Date? = null
)
