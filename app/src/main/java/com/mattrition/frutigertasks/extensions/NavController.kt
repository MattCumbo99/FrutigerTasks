package com.mattrition.frutigertasks.extensions

import androidx.navigation.NavController

/**
 * Saves data to be used with other screens.
 *
 * @param key Identifier for the information.
 * @param value Information to save.
 */
fun <T> NavController.set(key: String, value: T) =
    this.currentBackStackEntry?.savedStateHandle?.set(key, value)

/**
 * Retrieves data set by other screens.
 *
 * @param key Identifier of the requested information.
 * @return Data associated with the [key], or `null` if it hasn't been set.
 */
fun <T> NavController.get(key: String): T? = this.currentBackStackEntry?.savedStateHandle?.get(key)

fun <T> NavController.remove(key: String) =
    this.currentBackStackEntry?.savedStateHandle?.remove<T>(key)
