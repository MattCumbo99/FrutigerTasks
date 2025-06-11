package com.mattrition.frutigertasks.activities.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.mattrition.frutigertasks.R

@Composable
internal fun WithBackground(
    modifier: Modifier = Modifier,
    imageId: Int = R.drawable.aero_background,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize() // Stretch to fill the Box
        )

        Column(modifier = modifier) { content() }
    }
}
