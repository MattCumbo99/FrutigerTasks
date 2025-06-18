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
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder
import com.mattrition.frutigertasks.model.scheduler.RepeatableTime
import com.mattrition.frutigertasks.viewmodel.AddTaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

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
    var repeatSelected by remember { mutableIntStateOf(0) }

    val datePickerState = rememberDatePickerState()
    val selectedDate =
        datePickerState.selectedDateMillis?.let {
            // For some reason, the date picker selects a day before what the user selected,
            // so we need to add a whole day in milliseconds.
            it + DAY_IN_MILLIS
        } ?: addTaskViewModel.startDate

    fun setValues() {
        addTaskViewModel.startDate = selectedDate
        addTaskViewModel.repeats = repeatOptionsMap[repeatOptions[repeatSelected]]
    }

    ScreenBuilder(
        screenTitle = "Dates and repeats",
        navigateBack = { navController.popBackStack() },
        actions = {
            Button(
                onClick = {
                    navController.popBackStack()

                    setValues()
                }
            ) {
                Text("Save")
            }
        }
    ) {
        val modifier = Modifier.fillMaxWidth()

        Box(modifier = Modifier.fillMaxWidth()) {
            AeroTextField(
                value = convertMillisToDate(selectedDate),
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
            modifier = modifier
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
                .setPositiveButton("CANCEL") { dialog, which -> dialog.cancel() }
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
            Text(repeatOptions[repeatSelected])
        }
    }
}

private val repeatOptionsMap =
    mapOf(
        "Do not repeat" to null,
        "Daily" to RepeatableTime(TimeUnit.DAYS, 1),
        "Weekly" to RepeatableTime(TimeUnit.DAYS, 7)
    )

private val reminderOptions =
    arrayOf("On time", "1 minute before", "10 minutes before", "1 hour before", "1 day before")

private val repeatOptions =
    arrayOf(
        "Do not repeat",
        "Daily",
        "Weekly",
        "Weekdays",
        "Monthly",
        "Yearly",
        "Specific days of week",
        "Custom..."
    )

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    return formatter.format(Date(millis))
}
