package com.mattrition.frutigertasks.activities.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattrition.frutigertasks.activities.ui.theme.FrutigerTasksTheme

@Composable
fun AeroTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val shape = RoundedCornerShape(20.dp)

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .padding(5.dp)
            .shadow(
                elevation = 3.dp,
                shape = shape
            )
            .background(
                color = Color.White,
                shape = shape
            )
            .border(
                border = BorderStroke(6.dp, Brush.verticalGradient(
                    colors = listOf(
                        Color.LightGray,
                        Color.Gray
                    )
                )),
                shape = shape
            )
            .fillMaxWidth(),
        shape = shape,
        singleLine = true
    )
}

@Composable
@Preview(showBackground = true)
private fun AeroTextFieldPreview() {
    FrutigerTasksTheme {
        var prompt by remember { mutableStateOf("") }

        AeroTextField(
            label = "Text Field",
            value = prompt,
            onValueChange = { prompt = it }
        )
    }
}
