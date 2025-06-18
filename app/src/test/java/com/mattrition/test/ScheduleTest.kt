package com.mattrition.test

import com.mattrition.frutigertasks.model.scheduler.Schedule
import java.time.DayOfWeek
import java.util.Calendar
import org.junit.Before
import org.junit.Test

class ScheduleTest {

    private val pastCalendar: Calendar = Calendar.getInstance()
    private val presentCalendar: Calendar = Calendar.getInstance()
    private val futureCalendar: Calendar = Calendar.getInstance()

    @Before
    fun setTime() {
        // Saturday, February 1st 2025
        presentCalendar.set(Calendar.MONTH, Calendar.FEBRUARY)
        presentCalendar.set(Calendar.DAY_OF_MONTH, 1)
        presentCalendar.set(Calendar.YEAR, 2025)

        pastCalendar.time = presentCalendar.time
        pastCalendar.add(Calendar.DAY_OF_YEAR, -5) // 5 days ago

        futureCalendar.time = presentCalendar.time
        futureCalendar.add(Calendar.DAY_OF_YEAR, 5) // 5 days ahead
    }

    @Test
    fun testWeekends() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis,
                onDaysOfWeek = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
            )

        assert(schedule.isActive(presentCalendar.time))
    }

    @Test
    fun testWeekdays() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis,
                onDaysOfWeek =
                setOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
                )
            )

        assert(!schedule.isActive(presentCalendar.time))
    }

    @Test
    fun testLaterStartDate() {
        val schedule =
            Schedule(
                startDate = futureCalendar.timeInMillis,
                onDaysOfWeek = setOf(DayOfWeek.SATURDAY) // This should not matter
            )

        assert(!schedule.isActive(presentCalendar.time))
    }

    @Test
    fun testPastEndDate() {
        val startCal = Calendar.getInstance()
        startCal.time = presentCalendar.time
        startCal.add(Calendar.DAY_OF_YEAR, -10) // Started 10 days ago

        val schedule =
            Schedule(
                startDate = startCal.timeInMillis,
                endDate = pastCalendar.timeInMillis,
                onDaysOfWeek = setOf(DayOfWeek.SATURDAY)
            )

        assert(!schedule.isActive(presentCalendar.time))
    }

    @Test
    fun testRepeatDaily() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis,
                dailyRepeat = 5 // Repeat every 5 days
            )

        assert(schedule.isActive(presentCalendar.time))
        assert(schedule.isActive(futureCalendar.time))

        val schedule2 =
            Schedule(
                startDate = pastCalendar.timeInMillis,
                dailyRepeat = 6 // Every 6 days, so not today
            )

        assert(!schedule2.isActive(presentCalendar.time))
    }

    @Test
    fun testOnDayOfYear() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis,
                onDaysOfYear = setOf(presentCalendar.timeInMillis)
            )

        assert(schedule.isActive(presentCalendar.time))
        assert(!schedule.isActive(futureCalendar.time))
    }
}
