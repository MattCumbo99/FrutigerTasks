package com.mattrition.frutigertasks.activities.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * The foundation of a screen.
 *
 * @param screenTitle The name of the screen.
 * @param mainScreen If this screen is the first. Disables the back button.
 * @param navigateBack What happens if the user selects the back button.
 * @param actions Additional screen actions.
 * @param content The contents of the screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenBuilder(
    screenTitle: String,
    mainScreen: Boolean = false,
    navigateBack: () -> Unit = {},
    actions: @Composable (RowScope) -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { AeroTitle(screenTitle) },
                navigationIcon = {
                    if (!mainScreen) {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                actions = actions
            )
        }
    ) { innerPadding ->
        // Insert the screen contents
        WithBackground(Modifier.padding(innerPadding)) { content() }
    }
}
