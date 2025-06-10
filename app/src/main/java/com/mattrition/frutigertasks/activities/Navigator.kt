package com.mattrition.frutigertasks.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattrition.frutigertasks.activities.ui.theme.FrutigerTasksTheme

class Navigator : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrutigerTasksTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = ScreenId.MAIN.name) {
                    composable(ScreenId.MAIN.name) { MainActivity(navController, it) }

                    composable(ScreenId.ADD_TASK.name) { AddTaskActivity(navController, it) }

                    composable(ScreenId.DATE_AND_REPEATS.name) {
                        DateAndRepeatsForm(navController, it)
                    }
                }
            }
        }
    }
}
