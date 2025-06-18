package com.mattrition.frutigertasks.viewmodel

import androidx.lifecycle.ViewModel
import com.mattrition.frutigertasks.model.scheduler.RepeatableTime
import java.util.Date

class AddTaskViewModel(
    var name: String = "",
    var description: String = "",
    var startDate: Long = Date().time,
    var repeats: RepeatableTime? = null
) : ViewModel() {
    /** Resets the data values. */
    fun clear() {
        name = ""
        description = ""
        startDate = Date().time
        repeats = null
    }

    /** Checks if the form is valid. */
    fun isValid(): Boolean = this.name.isNotBlank()
}
