package com.mattrition.frutigertasks.activities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.mattrition.frutigertasks.activities.ui.common.AeroInfoButton
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.AeroTitle
import com.mattrition.frutigertasks.activities.ui.common.WithBackground

@Composable
fun AddTaskActivity(navController: NavController) {
    WithBackground {
        AeroTitle("Add Task")

        var taskName by remember { mutableStateOf("") }
        var taskDesc by remember { mutableStateOf("") }

        AeroTextField(
            label = "Name",
            value = taskName,
            onValueChange = { taskName = it }
        )

        AeroTextField(
            label = "Description",
            value = taskDesc,
            onValueChange = { taskDesc = it }
        )

        AeroInfoButton(
            onClick = {
                navController.popBackStack()
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("task_name", taskName)

                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("task_desc", taskDesc)
            },
            text = "Add"
        )
    }
}