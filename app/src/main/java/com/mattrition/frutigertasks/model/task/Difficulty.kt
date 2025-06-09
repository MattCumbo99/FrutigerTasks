package com.mattrition.frutigertasks.model.task

enum class Difficulty(val percentage: Double) {
    TRIVIAL(0.1), EASY(0.25), MEDIUM(0.5), HARD(0.75), INSANE(1.0)
}