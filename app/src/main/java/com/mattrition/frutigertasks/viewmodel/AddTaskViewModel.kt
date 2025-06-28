package com.mattrition.frutigertasks.viewmodel

import androidx.lifecycle.ViewModel
import com.mattrition.frutigertasks.model.scheduler.Schedule
import com.mattrition.frutigertasks.model.task.Difficulty
import com.mattrition.frutigertasks.model.task.Task
import java.util.UUID

typealias SkillId = String

typealias ImpactAmount = Double

/**
 * Represents the form fields for adding a task.
 *
 * @property name The name of the task
 * @property description Optional descriptor explaining the task
 * @property schedule When the task should be active
 * @property difficulty How difficult the task is to complete
 * @property skillIncreases A map containing information on which skill ID is impacted by a certain
 *   amount (as percentage, so 20% impact would be 0.2).
 */
class AddTaskViewModel(
    var name: String = "",
    var description: String = "",
    var schedule: Schedule = Schedule(),
    var difficulty: Difficulty = Difficulty.Easy,
    val skillIncreases: MutableMap<SkillId, ImpactAmount> = mutableMapOf()
) : ViewModel() {

    private val id = UUID.randomUUID().toString()

    /** Resets all properties to their default values. */
    fun clear() {
        name = ""
        description = ""
        schedule = Schedule()
        difficulty = Difficulty.Easy
        skillIncreases.clear()
    }

    /**
     * Checks if any of the properties of this form has been modified.
     *
     * @return true if at least one of the properties are not the default value
     */
    fun isModified(): Boolean = name.isNotBlank() ||
        description.isNotBlank() ||
        schedule != Schedule() ||
        difficulty != Difficulty.Easy ||
        skillIncreases.isNotEmpty()

    /** Checks if the form is valid. */
    fun isValid(): Boolean = this.name.isNotBlank()

    /**
     * Converts this view model's data to a [Task] object.
     *
     * @return this view model as a [Task] object
     */
    fun toTask(): Task = Task(
        id = id,
        name = name,
        description = description,
        schedule = schedule,
        difficulty = difficulty,
        skillIncreases = skillIncreases.toMap()
    )
}
