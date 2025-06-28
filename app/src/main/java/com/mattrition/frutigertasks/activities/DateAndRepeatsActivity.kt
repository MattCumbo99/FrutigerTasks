package com.mattrition.frutigertasks.activities

import android.app.AlertDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mattrition.frutigertasks.activities.ui.common.AeroCheckmarkButton
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder
import com.mattrition.frutigertasks.extensions.asDate
import com.mattrition.frutigertasks.extensions.fromDateToMillis
import com.mattrition.frutigertasks.extensions.reformatDate
import com.mattrition.frutigertasks.model.scheduler.Schedule
import com.mattrition.frutigertasks.viewmodel.AddTaskViewModel
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

private const val DAY_IN_MILLIS = 86400000

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O) // For using LocalDate
@Composable
fun DateAndRepeatsActivity(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    addTaskViewModel: AddTaskViewModel
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var repeatSelected by remember {
        val presetSchedules = repeatOptionsMap.values.toList()
        val scheduleIndex =
            presetSchedules.indexOf(addTaskViewModel.schedule).takeUnless { it == -1 }

        mutableIntStateOf(scheduleIndex ?: 0)
    }

    val startDate = addTaskViewModel.schedule.startDate

    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = startDate.fromDateToMillis(),
            selectableDates = PresentAndFutureDates
        )

    val selectedDate = datePickerState.selectedDateMillis?.asDate() ?: startDate

    fun setValues() {
        val mappedSchedule = repeatOptionsMap[repeatOptions[repeatSelected]]
        mappedSchedule?.let { addTaskViewModel.schedule = it }

        addTaskViewModel.schedule.startDate = selectedDate
    }

    ScreenBuilder(
        screenTitle = "Dates and repeats",
        navigateBack = { navController.popBackStack() },
        actions = {
            AeroCheckmarkButton {
                navController.popBackStack()
                setValues()
            }
        }
    ) {
        val modifier = Modifier.fillMaxWidth()

        // TODO Allow the user to set the date by clicking the text box as well
        Box(modifier = Modifier.fillMaxWidth()) {
            AeroTextField(
                value =
                selectedDate.reformatDate("MM/dd/yyyy", timeZone = TimeZone.getTimeZone("UTC")),
                label = "Start Date",
                readOnly = true,
                leadingIcon = {
                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp)
            ) {}

            if (showDatePicker) {
                Popup(
                    onDismissRequest = { showDatePicker = false },
                    alignment = Alignment.TopStart
                ) {
                    Box(
                        modifier =
                        Modifier.fillMaxWidth()
                            .offset(y = 64.dp)
                            .shadow(elevation = 4.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        DatePicker(state = datePickerState, showModeToggle = false)
                    }
                }
            }
        }

        Button(
            onClick = {
                // TODO Show time picker
            },
            modifier = modifier,
            enabled = false
        ) {
            Text("Time: 12:00am")
        }

        val reminderDialogBuilder =
            AlertDialog.Builder(LocalContext.current)
                .setTitle("Reminder")
                .setPositiveButton("OK") { dialog, which ->
                    // TODO Save selected options
                    dialog.cancel()
                }
                .setMultiChoiceItems(reminderOptions, null) { dialog, which, isChecked ->
                    // TODO Save selected options
                }
                .create()

        // Reminder dialog button
        Button(onClick = { reminderDialogBuilder.show() }, modifier = modifier) {
            Text("Add reminder")
        }

        val repeatDialogBuilder =
            AlertDialog.Builder(LocalContext.current)
                .setTitle("Repeat mode")
                .setPositiveButton("CANCEL") { dialog, _ -> dialog.cancel() }
                .setSingleChoiceItems(repeatOptionsMap.keys.toTypedArray(), repeatSelected) {
                        dialog,
                        which
                    ->
                    repeatSelected = which
                    dialog.cancel()
                }
                .create()

        // Repeat configuration
        Button(onClick = { repeatDialogBuilder.show() }, modifier = modifier) {
            val selectedRepeatable = repeatOptionsMap[repeatOptions[repeatSelected]]
            val repeatText = selectedRepeatable?.asRepeatString()

            Text(repeatText ?: "null")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private val repeatOptionsMap =
    mapOf(
        "Do not repeat" to Schedule(),
        "Daily" to Schedule(dailyRepeat = 1),
        "Weekly" to Schedule(dailyRepeat = 7),
        "Weekdays" to Schedule(onDaysOfWeek = Schedule.WEEKDAYS),
        "Weekends" to Schedule(onDaysOfWeek = Schedule.WEEKENDS),
        "1st day of month" to Schedule(onDayOfMonth = 1),
        "Yearly" to Schedule(onDaysOfYear = setOf(Date().time.asDate()))
    )

private val reminderOptions =
    arrayOf("On time", "1 minute before", "10 minutes before", "1 hour before", "1 day before")

private val repeatOptions =
    arrayOf(
        "Do not repeat",
        "Daily",
        "Weekly",
        "Weekdays",
        "Weekends",
        "1st day of month",
        "Yearly",
        "Specific days of week",
        "Custom..."
    )

@OptIn(ExperimentalMaterial3Api::class)
private object PresentAndFutureDates : SelectableDates {
    override fun isSelectableYear(year: Int): Boolean {
        val cal = Calendar.getInstance()
        val currentYear = cal.get(Calendar.YEAR)

        // Allow only a 1 year min and max
        return currentYear - 1 <= year && year <= currentYear + 1
    }
}
