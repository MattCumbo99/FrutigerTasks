package com.mattrition.test

import com.mattrition.frutigertasks.extensions.asDate
import com.mattrition.frutigertasks.extensions.fromDateToMillis
import com.mattrition.frutigertasks.extensions.reformatDate
import java.util.TimeZone
import org.junit.Test

class DateConversionTest {

    private val utcDate = "02/20/2025 00:00:00 UTC"
    private val dateMillis = 1740009600000
    private val cstTimeZone = TimeZone.getTimeZone("CST")
    private val usTimeZone = TimeZone.getTimeZone("America/Chicago")
    private val utcTimeZone = TimeZone.getTimeZone("UTC")

    @Test
    fun `should convert epoch milliseconds to UTC date`() {
        val result = dateMillis.asDate(timeZone = utcTimeZone)
        assert(result == utcDate) { "Expected $utcDate but was $result" }
    }

    @Test
    fun `should convert epoch milliseconds to CST date`() {
        val result = dateMillis.asDate(timeZone = cstTimeZone)
        val expected = "02/19/2025 18:00:00 CST"
        assert(result == expected) { "Expected $expected but was $result" }
    }

    @Test
    fun `should convert epoch milliseconds to Chicago date`() {
        val result = dateMillis.asDate(timeZone = usTimeZone)
        val expected = "02/19/2025 18:00:00 CST"
        assert(result == expected) { "Expected $expected but was $result" }
    }

    @Test
    fun `should convert UTC date to milliseconds`() {
        val result = utcDate.fromDateToMillis()
        assert(result == dateMillis) { "Expected $dateMillis but was $result" }
    }

    @Test
    fun `should convert CST date to milliseconds`() {
        val expected = 1740031200000
        val cstDate = "02/20/2025 00:00:00 CST"
        val result = cstDate.fromDateToMillis()
        assert(result == expected) { "Expected $expected but was $result" }
    }

    @Test
    fun `should correctly reformat UTC date`() {
        val initial = "07/23/1999 00:00:00 UTC"
        val expected = "07/23/1999"
        val actual = initial.reformatDate("MM/dd/yyyy", timeZone = TimeZone.getTimeZone("UTC"))

        assert(expected == actual) { "Expected $expected but was $actual" }
    }

    @Test
    fun `should correctly reformat CST date into UTC date`() {
        val initial = "07/22/1999 18:00:00 CST"
        val expected = "07/23/1999"
        val actual =
            initial.reformatDate("MM/dd/yyyy", timeZone = cstTimeZone, offsetTimeZone = utcTimeZone)

        assert(expected == actual) { "Expected $expected but was $actual" }
    }
}
