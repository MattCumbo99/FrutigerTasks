package com.mattrition.frutigertasks.viewmodel

import androidx.lifecycle.ViewModel
import java.util.Date

data class AddTaskViewModel(
    var name: String = "",
    var description: String = "",
    var startDate: Long = Date().time
) : ViewModel() {
    /** Resets the data values. */
    fun clear() {
        name = ""
        description = ""
        startDate = Date().time
    }
}
