package com.mattrition.frutigertasks.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mattrition.frutigertasks.R
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder
import com.mattrition.frutigertasks.extensions.get

@Composable
fun MainActivity(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    ScreenBuilder(screenTitle = "Frutiger Tasks", mainScreen = true) {
        UserCard(navController)

        Button(onClick = { navController.navigate(ScreenId.ADD_TASK.name) }) { Text("Add Task") }
    }
}

@Composable
private fun UserCard(navController: NavController) {
    val boxShape = RoundedCornerShape(size = 15.dp)

    // User's profile box
    Box(
        modifier =
        Modifier.padding(10.dp)
            .clickable { navController.navigate(ScreenId.EDIT_USER.name) }
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors =
                    listOf(
                        Color.Gray,
                        Color.LightGray,
                        Color.White,
                        Color.LightGray,
                        Color.Gray
                    )
                ),
                shape = boxShape
            )
            .border(3.dp, color = Color.Black, shape = boxShape)
            .padding(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.msn_guy),
                contentDescription = "Background"
            )

            Column {
                val txtSize = 4.em

                // TODO Replace this with user's name
                Text("MSN Clippy", fontSize = txtSize)
                Text("Novice", fontSize = txtSize)
                Text("Level 0", fontSize = txtSize)
            }
        }
    }
}
