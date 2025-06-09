package com.mattrition.frutigertasks.activities

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattrition.frutigertasks.R
import com.mattrition.frutigertasks.activities.ui.theme.FrutigerTasksTheme

class Navigator : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrutigerTasksTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenId.MAIN.name
                ) {
                    composable(ScreenId.MAIN.name) { navResult ->
                        MainActivity(navController, navResult)
                    }

                    composable(ScreenId.ADD_TASK.name) {
                        AddTaskActivity(navController)
                    }
                }
            }
        }
    }
}
