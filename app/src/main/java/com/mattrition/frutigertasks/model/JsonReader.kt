package com.mattrition.frutigertasks.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

/** Class used for reading json resource files. */
class JsonReader private constructor() {
    companion object {
        /**
         * Reads a JSON file containing an array of objects and returns it as a class.
         *
         * @param T The class to map the data as.
         * @param asset Filepath of the JSON data.
         * @return the contents of the file
         */
        inline fun <reified T> readJsonArrayFromAsset(asset: String): List<T> {
            val json =
                try {
                    File(asset).readLines()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    emptyList()
                }
                    .joinToString("")
                    .trimIndent()

            val type = TypeToken.getParameterized(List::class.java, T::class.java).type

            return Gson().fromJson(json, type)
        }

        inline fun <reified T> readJsonArrayFromAsset(file: Int, context: Context): List<T> {
            val json =
                try {
                    context.resources.openRawResource(file).reader().readLines()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    emptyList()
                }
                    .joinToString("")
                    .trimIndent()

            val type = TypeToken.getParameterized(List::class.java, T::class.java).type

            return Gson().fromJson(json, type)
        }
    }
}
