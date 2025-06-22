package com.mattrition.frutigertasks.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.mattrition.frutigertasks.R
import com.mattrition.frutigertasks.activities.ui.common.AeroCheckmarkButton
import com.mattrition.frutigertasks.activities.ui.common.ScreenBuilder
import com.mattrition.frutigertasks.model.JsonReader
import com.mattrition.frutigertasks.model.default.DefaultSkill
import com.mattrition.frutigertasks.viewmodel.AddTaskViewModel
import com.mattrition.frutigertasks.viewmodel.ImpactAmount
import com.mattrition.frutigertasks.viewmodel.SkillId

@Composable
fun SkillSelectActivity(navController: NavController, addTaskViewModel: AddTaskViewModel) {
    val skillsChanged = mutableMapOf<SkillId, ImpactAmount>()

    ScreenBuilder(
        screenTitle = "Select skills",
        navigateBack = { navController.popBackStack() },
        actions = {
            AeroCheckmarkButton {
                addTaskViewModel.increaseSkills.clear()
                addTaskViewModel.increaseSkills.putAll(skillsChanged)
                navController.popBackStack()
            }
        }
    ) {
        val defaultSkills =
            JsonReader.readJsonArrayFromAsset<DefaultSkill>(
                R.raw.default_skills,
                LocalContext.current
            )

        defaultSkills.forEach { skill ->
            var checked by remember {
                mutableStateOf(addTaskViewModel.increaseSkills.containsKey(skill.id))
            }
            val initialSliderPosition =
                addTaskViewModel.increaseSkills[skill.id]?.let { (it * 100).toInt() } ?: 100
            var sliderPosition by remember { mutableIntStateOf(initialSliderPosition) }

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.background(Color.White).fillMaxWidth().padding(10.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = checked, onCheckedChange = { checked = it })

                        Text(
                            skill.name,
                            fontSize = 5.em,
                            modifier = Modifier.fillMaxWidth().clickable { checked = !checked }
                        )
                    }

                    if (checked) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 20.dp)
                        ) {
                            Text("$sliderPosition%", fontSize = 5.em)

                            Slider(
                                value = sliderPosition.toFloat(),
                                onValueChange = { sliderPosition = it.toInt() },
                                valueRange = 0f..100f,
                                steps = 100
                            )
                        }

                        // Convert the final value to percentage
                        skillsChanged[skill.id] = sliderPosition.toDouble() * 0.01
                    } else {
                        skillsChanged.remove(skill.id)
                    }
                }
            }
        }
    }
}

private val viewModel = AddTaskViewModel()

@Composable
@Preview(showBackground = true)
private fun PreviewSkillSelectActivity() {
    SkillSelectActivity(NavController(LocalContext.current), viewModel)
}
