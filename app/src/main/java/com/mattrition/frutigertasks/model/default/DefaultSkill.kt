package com.mattrition.frutigertasks.model.default

/** Contains the template used to read data from `default_skills.json` */
data class DefaultSkill(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val statsImpacted: List<ImpactedStat>
)
