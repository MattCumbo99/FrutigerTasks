package com.mattrition.frutigertasks.model.scheduler

import java.util.concurrent.TimeUnit

/**
 * Data class representing an identifier for a "repeatable" amount of time.
 *
 * Example:
 * ```
 * // Repeat every two weeks
 * val twoWeeks = RepeatTime(unit = TimeUnit.DAYS, amount = 14)
 * ```
 *
 * @property unit Time unit this repeatable will base off of
 * @property amount The amount of time units
 */
data class RepeatableTime(val unit: TimeUnit, val amount: Int)
