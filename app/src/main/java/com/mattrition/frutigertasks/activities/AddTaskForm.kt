package com.mattrition.frutigertasks.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mattrition.frutigertasks.activities.ui.common.AeroInfoButton
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder

@Composable
fun AddTaskActivity(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    ScreenBuilder(screenTitle = "Add Task", navigateBack = { navController.popBackStack() }) {
        var taskName by remember { mutableStateOf("") }
        AeroTextField(label = "Name", value = taskName, onValueChange = { taskName = it })

        var taskDesc by remember { mutableStateOf("") }
        AeroTextField(label = "Description", value = taskDesc, onValueChange = { taskDesc = it })

        Box(
            modifier =
            Modifier.fillMaxWidth()
                .clickable { navController.navigate(ScreenId.DATE_AND_REPEATS.name) }
                .background(Color.Gray)
        ) {
            Column {
                Text("Date: ")
                Text("Time: ")
                Text("Repeats on:")
            }
        }

        AeroInfoButton(
            onClick = {
                navController.popBackStack()
                navController.currentBackStackEntry?.savedStateHandle?.set("task_name", taskName)

                navController.currentBackStackEntry?.savedStateHandle?.set("task_desc", taskDesc)
            },
            text = "Add",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
