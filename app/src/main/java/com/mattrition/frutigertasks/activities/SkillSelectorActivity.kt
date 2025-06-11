package com.mattrition.frutigertasks.activities

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder

@Composable
fun SkillSelectorActivity(navController: NavController) {
    ScreenBuilder(screenTitle = "Select skills", navigateBack = { navController.popBackStack() }) {}
}
