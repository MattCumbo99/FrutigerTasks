package com.mattrition.frutigertasks.activities

import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder
import com.mattrition.frutigertasks.extensions.get
import com.mattrition.frutigertasks.extensions.set
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*
 * For some reason, the date picker selects a day before what the user selected,
 * so we need to add a whole day in milliseconds.
 */
private const val DAY_IN_MILLIS = 86400000

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O) // For using LocalDate
@Composable
fun DateAndRepeatsActivity(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate =
        navController.get<String>("date_start") // FIXME The date does not change after saving
            ?: datePickerState.selectedDateMillis?.let { convertMillisToDate(it + DAY_IN_MILLIS) }
            ?: convertMillisToDate(Date().time)

    fun setValues() {
        navController.set("date_start", selectedDate)
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
                value = selectedDate,
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

        Button(
            onClick = {
                // TODO Show pop-up of repeatable options
            },
            modifier = modifier
        ) {
            Text("Do not repeat")
        }
    }
}

private val repeatOptions =
    listOf(
        "Do not repeat",
        "Daily",
        "Weekly",
        "Weekdays",
        "Monthly",
        "Yearly",
        "Specific days of week",
        "Custom..."
    )

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    Log.w("DEBUG", "Millis: $millis, TimeZone: ${formatter.timeZone}")
    return formatter.format(Date(millis))
}
