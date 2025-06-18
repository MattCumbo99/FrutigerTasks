package com.mattrition.frutigertasks.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mattrition.frutigertasks.R
import com.mattrition.frutigertasks.activities.ui.common.AeroCheckmarkButton
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder

@Composable
fun EditUserActivity(navController: NavController) {
    ScreenBuilder(
        screenTitle = "Edit user",
        navigateBack = { navController.popBackStack() },
        actions = {
            AeroCheckmarkButton {
                // TODO Save user information
            }
        }
    ) {
        Box(modifier = Modifier.padding(5.dp).fillMaxWidth()) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.msn_guy),
                    contentDescription = "MSN Clippy",
                    modifier = Modifier.size(60.dp)
                )

                AeroTextField(label = "Username", value = "MSN Guy") {}
            }
        }
    }
}
