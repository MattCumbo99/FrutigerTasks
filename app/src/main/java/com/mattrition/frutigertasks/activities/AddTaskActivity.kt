package com.mattrition.frutigertasks.activities

import android.app.AlertDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mattrition.frutigertasks.R
import com.mattrition.frutigertasks.activities.ui.common.AeroTextField
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder
import com.mattrition.frutigertasks.extensions.reformatDate
import com.mattrition.frutigertasks.model.JsonReader
import com.mattrition.frutigertasks.model.default.DefaultSkill
import com.mattrition.frutigertasks.model.task.Difficulty
import com.mattrition.frutigertasks.viewmodel.AddTaskViewModel
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskActivity(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    addTaskViewModel: AddTaskViewModel
) {
    var taskName by remember { mutableStateOf(addTaskViewModel.name) }
    var taskDesc by remember { mutableStateOf(addTaskViewModel.description) }
    var difficultySelection by remember {
        mutableIntStateOf(difficulties.indexOf(addTaskViewModel.difficulty))
    }

    fun setValues() {
        addTaskViewModel.name = taskName
        addTaskViewModel.description = taskDesc
    }

    ScreenBuilder(
        screenTitle = "Add Task",
        navigateBack = {
            addTaskViewModel.clear()
            navController.popBackStack()
        },
        actions = {
            Button(
                onClick = {
                    setValues()
                    if (addTaskViewModel.isValid()) {
                        // TODO Save all task information to internal storage
                        // Navigate back
                        navController.popBackStack()
                    }
                }
            ) {
                Text("Save")
            }
        }
    ) {
        AeroTextField(label = "Name", value = taskName, onValueChange = { taskName = it })

        AeroTextField(label = "Description", value = taskDesc, onValueChange = { taskDesc = it })

        val startDate =
            addTaskViewModel.schedule.startDate.reformatDate(
                "MM/dd/yyyy",
                timeZone = TimeZone.getTimeZone("UTC")
            )

        // Date and repeats
        Box(
            modifier =
            Modifier.boxClickable {
                // Save contents before moving to next screen
                setValues()

                navController.navigate(ScreenId.DATE_AND_REPEATS.name)
            }
        ) {
            Column {
                BoxText("Start date: $startDate")
                BoxText(addTaskViewModel.schedule.asRepeatString())
                BoxText("Do not notify")
            }
        }

        // Difficulty dialog display
        val difficultyDialogBuilder =
            AlertDialog.Builder(LocalContext.current)
                .setTitle("Difficulty")
                .setPositiveButton("CANCEL") { dialog, _ -> dialog.cancel() }
                .setSingleChoiceItems(
                    difficulties.map { it.name }.toTypedArray(),
                    difficultySelection
                ) { dialog, which ->
                    difficultySelection = which
                    addTaskViewModel.difficulty = difficulties[which]
                    dialog.cancel()
                }
                .create()

        Button(onClick = { difficultyDialogBuilder.show() }, modifier = Modifier.fillMaxWidth()) {
            val diffText = difficulties[difficultySelection].name
            Text("Difficulty: $diffText")
        }

        Box(
            modifier =
            Modifier.boxClickable {
                // Save contents before moving to next screen
                setValues()

                navController.navigate(ScreenId.SELECT_SKILLS.name)
            }
        ) {
            val skills = addTaskViewModel.skillIncreases
            val text =
                if (skills.entries.isEmpty()) {
                    "Add increasing skill(s)"
                } else {
                    val skillSet =
                        JsonReader.readJsonArrayFromAsset<DefaultSkill>(
                            R.raw.default_skills,
                            LocalContext.current
                        )
                            .toSet()
                    val strSkills =
                        skills.entries.map { (skillId, impact) ->
                            val skillName = skillSet.first { it.id == skillId }.name
                            val percent = (impact * 100).toInt()

                            "$skillName $percent%"
                        }

                    val increases = strSkills.joinToString(", ")
                    "Increases skills: $increases"
                }
            BoxText(text)
        }
    }
}

private val difficulties = Difficulty.entries.toList()

private fun Modifier.boxClickable(clicked: () -> Unit) = this.padding(5.dp) // Outer padding
    .fillMaxWidth()
    .clickable { clicked() }
    .border(BorderStroke(2.dp, Color.Black))
    .background(Color.Gray)
    .padding(10.dp) // Inner content padding

@Composable private fun BoxText(text: String) = Text(text, fontSize = 4.em)
