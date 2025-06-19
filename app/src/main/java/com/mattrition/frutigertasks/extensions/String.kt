package com.mattrition.frutigertasks.extensions

fun String.sentenceCase() = this.lowercase().replaceFirstChar { it.uppercase() }
