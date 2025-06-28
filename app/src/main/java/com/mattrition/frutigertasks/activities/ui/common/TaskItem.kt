package com.mattrition.frutigertasks.activities.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.mattrition.frutigertasks.model.task.Task

@Composable
fun TaskItem(task: Task) {
    Box(modifier = Modifier.fillMaxWidth().padding(5.dp).background(Color.Gray).padding(5.dp)) {
        // TODO show next or current active date
        Column { Text(task.name, fontSize = 5.em) }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewTaskItem() {
    ScreenBuilder(screenTitle = "Task List") { TaskItem(Task(name = "Make bed")) }
}
