package com.mattrition.frutigertasks.viewmodel

import androidx.lifecycle.ViewModel
import com.mattrition.frutigertasks.model.scheduler.Schedule

class AddTaskViewModel(
    var name: String = "",
    var description: String = "",
    var schedule: Schedule = Schedule()
) : ViewModel() {
    /** Resets the data values. */
    fun clear() {
        name = ""
        description = ""
        schedule = Schedule()
    }

    /** Checks if the form is valid. */
    fun isValid(): Boolean = this.name.isNotBlank()
}
