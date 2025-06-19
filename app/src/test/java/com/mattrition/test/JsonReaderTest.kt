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
    fun testReadStatistics() {
        val results = JsonReader.readJsonArrayFromAsset<DefaultStatistic>(statFile)

        assert(results.isNotEmpty())
    }

    @Test
    fun testReadSkills() {
        val results = JsonReader.readJsonArrayFromAsset<DefaultSkill>(skillFile)

        assert(results.isNotEmpty())
    }
}
