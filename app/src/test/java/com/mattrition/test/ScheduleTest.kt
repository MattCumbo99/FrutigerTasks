package com.mattrition.test

import com.mattrition.frutigertasks.extensions.asDate
import com.mattrition.frutigertasks.extensions.fromDateToMillis
import com.mattrition.frutigertasks.extensions.removeTime
import com.mattrition.frutigertasks.model.scheduler.Schedule
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.Calendar
import java.util.TimeZone
import org.junit.Before
import org.junit.Test

private const val EXPECT_TRUE = "Expected true but was false"
private const val EXPECT_FALSE = "Expected false but was true"

class ScheduleTest {

    private val utcTimezone = TimeZone.getTimeZone("UTC")

    /** A calendar representing Monday, January 27 2025 */
    private val pastCalendar = Calendar.getInstance(utcTimezone)

    /** A calendar representing Saturday, February 1 2025 */
    private val presentCalendar = Calendar.getInstance(utcTimezone)

    /** A calendar representing Thursday, February 6 2025 */
    private val futureCalendar = Calendar.getInstance(utcTimezone)

    @Before
    fun setTime() {
        // Saturday, February 1st 2025
        presentCalendar.set(Calendar.MONTH, Calendar.FEBRUARY)
        presentCalendar.set(Calendar.DAY_OF_MONTH, 1)
        presentCalendar.set(Calendar.YEAR, 2025)
        presentCalendar.removeTime()

        pastCalendar.time = presentCalendar.time
        pastCalendar.add(Calendar.DAY_OF_YEAR, -5) // 5 days ago (Monday)

        futureCalendar.time = presentCalendar.time
        futureCalendar.add(Calendar.DAY_OF_YEAR, 5) // 5 days ahead (Thursday)
    }

    @Test
    fun `schedule should be active on weekends`() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis.asDate(),
                onDaysOfWeek = Schedule.WEEKENDS
            )

        assert(schedule.isActive(presentCalendar.timeInMillis.asDate(), utcTimezone)) {
            EXPECT_TRUE
        }
    }

    @Test
    fun `schedule should not be active during weekdays`() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis.asDate(),
                onDaysOfWeek = Schedule.WEEKDAYS
            )

        assert(!schedule.isActive(presentCalendar.timeInMillis.asDate(), utcTimezone)) {
            EXPECT_FALSE
        }
    }

    @Test
    fun `schedule should not be active until start date`() {
        val schedule =
            Schedule(
                startDate = futureCalendar.timeInMillis.asDate(),
                dailyRepeat = 1,
                onDaysOfWeek = setOf(DayOfWeek.SATURDAY),
                onDaysOfYear = setOf(presentCalendar.timeInMillis.asDate())
            )

        assert(!schedule.isActive(presentCalendar.timeInMillis.asDate(), utcTimezone)) {
            EXPECT_FALSE
        }
    }

    @Test
    fun `schedule should not be active after end date`() {
        val startCal = Calendar.getInstance(utcTimezone)
        startCal.time = presentCalendar.time
        startCal.add(Calendar.DAY_OF_YEAR, -10) // Started 10 days ago

        val schedule =
            Schedule(
                startDate = startCal.timeInMillis.asDate(),
                endDate = pastCalendar.timeInMillis.asDate(),
                dailyRepeat = 1,
                onDaysOfWeek = setOf(DayOfWeek.SATURDAY)
            )

        assert(!schedule.isActive(presentCalendar.timeInMillis.asDate(), utcTimezone)) {
            EXPECT_FALSE
        }
    }

    @Test
    fun `schedule that repeats every 5 days from 5 days ago should be active`() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis.asDate(),
                dailyRepeat = 5 // Repeat every 5 days
            )

        assert(schedule.isActive(presentCalendar.timeInMillis.asDate(), utcTimezone)) {
            EXPECT_TRUE
        }
    }

    @Test
    fun `schedule that repeats every 5 days from 10 days ago should be active`() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis.asDate(),
                dailyRepeat = 5 // Repeat every 5 days
            )

        assert(schedule.isActive(futureCalendar.timeInMillis.asDate(), utcTimezone)) { EXPECT_TRUE }
    }

    @Test
    fun `schedule that repeats every 3 days from 5 days ago should not be active`() {
        val schedule = Schedule(startDate = pastCalendar.timeInMillis.asDate(), dailyRepeat = 3)

        assert(!schedule.isActive(presentCalendar.timeInMillis.asDate(), utcTimezone)) {
            EXPECT_FALSE
        }
    }

    @Test
    fun `schedule should be active only on certain days of the year`() {
        val schedule =
            Schedule(
                startDate = pastCalendar.timeInMillis.asDate(),
                onDaysOfYear =
                setOf(
                    presentCalendar.timeInMillis.asDate(),
                    futureCalendar.timeInMillis.asDate()
                )
            )

        val changingCalendar = Calendar.getInstance(utcTimezone)
        changingCalendar.set(Calendar.YEAR, presentCalendar.get(Calendar.YEAR))
        changingCalendar.set(Calendar.DAY_OF_YEAR, 1)

        val fmt = SimpleDateFormat("MMM d")
        val validDates = schedule.onDaysOfYear.map { it.fromDateToMillis().asDate("MMM d") }

        for (day in 1..365) {
            changingCalendar.set(Calendar.DAY_OF_YEAR, day)

            val currentDate = fmt.format(changingCalendar.time)
            val isActive = schedule.isActive(changingCalendar.timeInMillis.asDate(), utcTimezone)

            if (currentDate in validDates) {
                assert(isActive) { "$EXPECT_TRUE for $currentDate. Valid dates: $validDates" }
            } else {
                assert(!isActive) { "$EXPECT_FALSE for $currentDate. Valid dates: $validDates" }
            }
        }
    }

    @Test
    fun `repeat string should show correct day of the week from start date`() {
        val schedule = Schedule(startDate = presentCalendar.timeInMillis.asDate(), dailyRepeat = 7)

        val expected = "Repeats on Saturdays"
        val actual = schedule.asRepeatString()

        assert(expected == actual) { "Expected [$expected] but was [$actual]" }
    }
}
