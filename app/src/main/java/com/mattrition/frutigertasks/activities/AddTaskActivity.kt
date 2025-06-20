package com.mattrition.frutigertasks.activities

import android.app.AlertDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder
import com.mattrition.frutigertasks.extensions.reformatDate
import com.mattrition.frutigertasks.model.task.Difficulty
import com.mattrition.frutigertasks.viewmodel.AddTaskViewModel
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskActivity(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    addTaskViewModel: AddTaskViewModel
) {
    var taskName by remember { mutableStateOf(addTaskViewModel.name) }
    var taskDesc by remember { mutableStateOf(addTaskViewModel.description) }
    var difficultySelection by remember { mutableIntStateOf(1) }

    fun setValues() {
        addTaskViewModel.name = taskName
        addTaskViewModel.description = taskDesc
    }

    ScreenBuilder(
        screenTitle = "Add Task",
        navigateBack = {
            addTaskViewModel.clear()
            navController.popBackStack()
        },
        actions = {
            Button(
                onClick = {
                    setValues()
                    if (addTaskViewModel.isValid()) {
                        // TODO Save all task information to internal storage
                        // Navigate back
                        navController.popBackStack()
                    }
                }
            ) {
                Text("Save")
            }
        }
    ) {
        AeroTextField(label = "Name", value = taskName, onValueChange = { taskName = it })

        AeroTextField(label = "Description", value = taskDesc, onValueChange = { taskDesc = it })

        fun boxClickable(clicked: () -> Unit) = Modifier.padding(5.dp) // Outer padding
            .fillMaxWidth()
            .clickable { clicked() }
            .border(BorderStroke(2.dp, Color.Black))
            .background(Color.Gray)
            .padding(10.dp) // Inner content padding

        @Composable fun BoxText(text: String) = Text(text, fontSize = 4.em)

        val startDate =
            addTaskViewModel.schedule.startDate.reformatDate(
                "MM/dd/yyyy",
                timeZone = TimeZone.getTimeZone("UTC")
            )

        // Date and repeats
        Box(
            modifier =
            boxClickable {
                // Save contents before moving to next screen
                setValues()

                navController.navigate(ScreenId.DATE_AND_REPEATS.name)
            }
        ) {
            Column {
                BoxText("Start date: $startDate")
                // TODO Save repeat information
                BoxText(addTaskViewModel.schedule.asRepeatString())
                BoxText("Do not notify")
            }
        }

        // Difficulty dialog display
        val difficultyDialogBuilder =
            AlertDialog.Builder(LocalContext.current)
                .setTitle("Difficulty")
                .setPositiveButton("CANCEL") { dialog, which -> dialog.cancel() }
                .setSingleChoiceItems(
                    Difficulty.entries.map { it.name }.toTypedArray(),
                    difficultySelection
                ) { dialog, which ->
                    difficultySelection = which
                    dialog.cancel()
                }
                .create()

        Button(onClick = { difficultyDialogBuilder.show() }, modifier = Modifier.fillMaxWidth()) {
            Text("Difficulty: ${Difficulty.entries[difficultySelection].name}")
        }

        Box(
            modifier =
            boxClickable {
                // Save contents before moving to next screen
                setValues()

                // TODO Navigate to add skill screen
            }
        ) {
            BoxText("Add increasing skill(s)")
        }

        Box(
            modifier =
            boxClickable {
                // Save contents before moving to next screen
                setValues()

                // TODO Navigate to add skill screen
            }
        ) {
            BoxText("Add decreasing skill(s)")
        }
    }
}
