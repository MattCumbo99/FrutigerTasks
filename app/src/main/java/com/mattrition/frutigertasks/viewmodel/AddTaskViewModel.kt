package com.mattrition.frutigertasks.viewmodel

import androidx.lifecycle.ViewModel
import com.mattrition.frutigertasks.model.scheduler.Schedule

typealias SkillId = String

typealias ImpactAmount = Double

class AddTaskViewModel(
    var name: String = "",
    var description: String = "",
    var schedule: Schedule = Schedule(),
    val increaseSkills: MutableMap<SkillId, ImpactAmount> = mutableMapOf()
) : ViewModel() {
    /** Resets the data values. */
    fun clear() {
        name = ""
        description = ""
        schedule = Schedule()
        increaseSkills.clear()
    }

    /** Checks if the form is valid. */
    fun isValid(): Boolean = this.name.isNotBlank()
}
