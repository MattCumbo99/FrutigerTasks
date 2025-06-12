package com.mattrition.frutigertasks.activities.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.mattrition.frutigertasks.R
import com.mattrition.frutigertasks.activities.ui.theme.DenimBlue
import com.mattrition.frutigertasks.activities.ui.theme.RoyalBlue

@Composable
fun AeroButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier =
        Modifier.padding(5.dp)
            .clickable { onClick }
            .background(
                brush = Brush.verticalGradient(listOf(Color.White, RoyalBlue, DenimBlue)),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(15.dp)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 5.em, color = Color.White)
    }
}

@Composable
fun AeroCheckmarkButton(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.check_button),
        contentDescription = "Checkmark box",
        modifier = Modifier.padding(5.dp).clickable { onClick }
    )
}

@Composable
@Preview(showBackground = true)
private fun AeroButtonPreview() {
    ScreenBuilder(
        screenTitle = "Simple buttons",
        mainScreen = true,
        actions = { AeroCheckmarkButton {} }
    ) {
        AeroButton(text = "Add", onClick = {})

        AeroButton(text = "Add", onClick = {}, modifier = Modifier.fillMaxWidth())
    }
}
