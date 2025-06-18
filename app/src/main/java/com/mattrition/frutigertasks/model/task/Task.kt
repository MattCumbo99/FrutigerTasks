package com.mattrition.frutigertasks.model.task

import com.mattrition.frutigertasks.model.scheduler.Schedule

/**
 * Represents a scheduled task for the user.
 *
 * @param name Name of the task.
 * @param description Description of the task.
 * @param schedule The schedule of the task.
 * @param difficultyScale Percentage of how difficult the task is.
 * @param subTasks A list of sub-tasks associated with this task.
 * @param categories Groups to categorize this task under.
 */
data class Task(
    val name: String,
    val description: String = "",
    val schedule: Schedule = Schedule(),
    val difficultyScale: Double = Difficulty.Easy.percentage,
    val subTasks: MutableList<SubTask> = mutableListOf(),
    val categories: MutableSet<String> = mutableSetOf()
)
