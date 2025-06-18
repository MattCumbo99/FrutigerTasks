package com.mattrition.frutigertasks.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattrition.frutigertasks.activities.ui.theme.FrutigerTasksTheme
import com.mattrition.frutigertasks.viewmodel.AddTaskViewModel

/** This class represents the navigable part of the app. */
class Navigator : ComponentActivity() {

    private lateinit var addTaskViewModel: AddTaskViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addTaskViewModel = ViewModelProvider(this)[AddTaskViewModel::class]

        enableEdgeToEdge()
        setContent {
            FrutigerTasksTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = ScreenId.MAIN.name) {
                    composable(ScreenId.MAIN.name) { MainActivity(navController, it) }

                    composable(ScreenId.ADD_TASK.name) {
                        AddTaskActivity(navController, it, addTaskViewModel)
                    }

                    composable(ScreenId.EDIT_USER.name) { EditUserActivity(navController) }

                    composable(ScreenId.DATE_AND_REPEATS.name) {
                        DateAndRepeatsActivity(navController, it, addTaskViewModel)
                    }
                }
            }
        }
    }
}
