package com.mattrition.frutigertasks.activities

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder
import com.mattrition.frutigertasks.extensions.get
import com.mattrition.frutigertasks.extensions.set
import com.mattrition.frutigertasks.viewmodel.AddTaskViewModel

@Composable
fun AddTaskActivity(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    addTaskViewModel: AddTaskViewModel
) {
    var taskName by remember { mutableStateOf(addTaskViewModel.name) }

    var taskDesc by remember { mutableStateOf(addTaskViewModel.description) }

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
                    // Navigate back
                    navController.popBackStack()

                    // Send the information back to the main screen
                    setValues()
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

        val startDate = remember { navController.get<String>("date_start") ?: "Today" }

        // Date and repeats
        Box(
            modifier =
            boxClickable {
                // Save contents before moving to next screen
                setValues()

                navController.navigate(ScreenId.DATE_AND_REPEATS.name)

                if (startDate != "Today") navController.set("date_start", startDate)
            }
        ) {
            Column {
                BoxText("Start date: $startDate")
                BoxText("Do not repeat")
                BoxText("Do not notify")
            }
        }

        Button(
            onClick = {
                // TODO Display dialog box with difficulty options
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Difficulty: Easy")
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
