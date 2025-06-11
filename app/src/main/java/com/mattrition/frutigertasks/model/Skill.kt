package com.mattrition.frutigertasks.model

/**
 * Represents a skill that should increase in XP when tasks are completed.
 *
 * @param id Identification reference to this skill.
 * @param name Name of the skill.
 * @param description Description of the skill.
 * @param initialLevel Starting level of the skill.
 * @param statsImpacted A map containing information related to how impactful it is on the user's
 *   stats.
 */
data class Skill(
    val id: String,
    val name: String,
    val description: String = "",
    val initialLevel: Int = 1,
    val statsImpacted: MutableMap<String, Double> = mutableMapOf()
)
