package com.mattrition.test

import com.mattrition.frutigertasks.model.JsonReader
import com.mattrition.frutigertasks.model.default.DefaultSkill
import com.mattrition.frutigertasks.model.default.DefaultStatistic
import org.junit.Test

class JsonReaderTest {

    private val resDirectory = "src/main/res/raw"
    private val statFile = "$resDirectory/default_statistics.json"
    private val skillFile = "$resDirectory/default_skills.json"

    @Test
    fun `should read from default statistics file`() {
        val results = JsonReader.readJsonArrayFromAsset<DefaultStatistic>(statFile)

        assert(results.isNotEmpty()) { "Expected results to not be empty" }
    }

    @Test
    fun `should read from default skills file`() {
        val results = JsonReader.readJsonArrayFromAsset<DefaultSkill>(skillFile)

        assert(results.isNotEmpty()) { "Expected results to not be empty" }
    }
}
