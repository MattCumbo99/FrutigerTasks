package com.mattrition.frutigertasks.activities.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattrition.frutigertasks.activities.ui.theme.FrutigerTasksTheme

@Composable
fun AeroButton(
    text: String,
    colors: List<Color>,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(colors),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 5.dp
                )
        ) {
            Text(
                text = text
            )
        }
    }
}

@Composable
fun AeroAcceptButton(
    onClick: () -> Unit,
    text: String = "Accept"
) = AeroButton(text, listOf(Color.LightGray, Color.Green), onClick)

@Composable
fun AeroDeclineButton(
    onClick: () -> Unit,
    text: String = "Decline"
) = AeroButton(text, listOf(Color.LightGray, Color.Red), onClick)

@Composable
fun AeroInfoButton(
    onClick: () -> Unit,
    text: String = "Info"
) = AeroButton(text, listOf(Color.LightGray, Color.Blue), onClick)

@Composable
@Preview(showBackground = true)
private fun AeroButtonPreview() {
    FrutigerTasksTheme {
        Column {
            AeroAcceptButton({})
            AeroDeclineButton({})
            AeroInfoButton({})
        }
    }
}
