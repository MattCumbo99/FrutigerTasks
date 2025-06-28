package com.mattrition.frutigertasks.model.task

import com.mattrition.frutigertasks.model.scheduler.Schedule
import com.mattrition.frutigertasks.viewmodel.ImpactAmount
import com.mattrition.frutigertasks.viewmodel.SkillId
import java.util.UUID

/**
 * Represents a scheduled task for the user.
 *
 * @property id Unique identifier for this task
 * @property name Name of the task
 * @property description Descriptor explaining the task
 * @property schedule Schedule for when the task will be due
 * @property difficulty How hard the task is to complete
 * @property skillIncreases A map containing the impact on certain skills by their IDs
 */
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val schedule: Schedule = Schedule(),
    val difficulty: Difficulty = Difficulty.Easy,
    val skillIncreases: Map<SkillId, ImpactAmount> = emptyMap()
)
