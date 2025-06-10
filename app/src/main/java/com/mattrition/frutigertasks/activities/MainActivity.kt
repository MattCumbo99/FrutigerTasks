package com.mattrition.frutigertasks.activities

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.AeroTitle
import com.mattrition.frutigertasks.activities.ui.common.WithBackground

@Composable
fun MainActivity(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    WithBackground {
        AeroTitle("Frutiger Tasks")

        var answer by remember { mutableStateOf("") }

        AeroTextField(label = "Input", value = answer, onValueChange = { answer = it })

        Button(onClick = { navController.navigate(ScreenId.ADD_TASK.name) }) { Text("Add Task") }

        navBackStackEntry.savedStateHandle.get<String>("task_name")?.let { Text(it) }
    }
}
