package com.mattrition.frutigertasks.activities.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import com.mattrition.frutigertasks.activities.ui.theme.FrutigerTasksTheme

@Composable
fun AeroTitle(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 6.em,
        fontStyle = FontStyle.Italic,
        style = TextStyle(shadow = Shadow(blurRadius = 5F, color = Color.Black))
    )
}

@Composable
@Preview(showBackground = true)
private fun AeroTitlePreview() {
    FrutigerTasksTheme { AeroTitle("Frutiger Tasks") }
}
