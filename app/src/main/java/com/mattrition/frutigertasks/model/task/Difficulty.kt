package com.mattrition.frutigertasks.model.task

/**
 * Represents a pre-defined percentage based on difficulty level.
 *
 * @param percentage How difficult the value is out of 1.
 */
enum class Difficulty(val percentage: Double) {
    Trivial(0.1),
    Easy(0.25),
    Medium(0.5),
    Hard(0.75),
    Insane(1.0)
}
