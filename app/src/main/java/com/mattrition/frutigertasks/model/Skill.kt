package com.mattrition.frutigertasks.model

data class Skill(
    val name: String,
    val description: String = "",
    val statsImpacted: MutableMap<String, Double> = mutableMapOf()
)
