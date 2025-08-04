package com.samuel.oremoschanganapt

import android.content.Context
//import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.samuel.oremoschanganapt.ui.theme.Green
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

val Context.dataStore by preferencesDataStore(name = "settings_preferences")

val themeColor = intPreferencesKey("themeColor")
val secondThemeColor = intPreferencesKey("secondThemeColor")
val themeMode = stringPreferencesKey("themeMode")
val fontSize = stringPreferencesKey("fontSize")
val locale = stringPreferencesKey("locale")
val praysId = stringPreferencesKey("praysIds")
val songsId = stringPreferencesKey("songsIds")

enum class SetIdPreference(val preferenceName: String) {
    PRAYS_ID("praysIds"),
    SONGS_ID("songsIds")
}

enum class AppThemeColors(val value: String) {
    PRIMARY_COLOR("themeColor"),
    SECOND_COLOR("secondThemeColor")
}

// ThemeMode ------------>>
suspend fun saveThemeMode(context: Context, mode: String) {
    context.dataStore.edit { preferences ->
        preferences[themeMode] = mode
    }
}

suspend fun getInitialThemeMode(context: Context): String {
    val mode = context.dataStore.data.firstOrNull()?.get(themeMode) ?: "System"
    return if (mode == "404" || mode == "") "System" else mode
}

// ThemeColor ------------>>
@RequiresApi(Build.VERSION_CODES.O)
suspend fun saveThemeColor(context: Context, color: Color) {
    context.dataStore.edit { preferences ->
        preferences[themeColor] = color.toArgb()
    }
}

fun getThemeColor(context: Context): Flow<Color> {
    return context.dataStore.data.map { preferences ->
        val argb = preferences[themeColor] ?: Color.Unspecified.toArgb()
        Color(argb)
    }
}

suspend fun getInitialThemeColor(context: Context): Color {
    val colorInt = context.dataStore.data
        .firstOrNull()
        ?.get(key = themeColor)
        ?: Green.toArgb()
    return Color(colorInt)
}


// Second ThemeColor (for gradient) ------------>>
@RequiresApi(Build.VERSION_CODES.O)
suspend fun saveSecondThemeColor(context: Context, color: Color) {
    context.dataStore.edit { preferences ->
        preferences[secondThemeColor] = color.toArgb()
    }
}

fun getSecondThemeColor(context: Context): Flow<Color> {
    return context.dataStore.data.map { preferences ->
        val argb = preferences[secondThemeColor] ?: Color.Transparent.toArgb()
        Color(argb)
    }
}

suspend fun getInitialSecondThemeColor(context: Context): Color {
    val colorInt = context.dataStore.data
        .firstOrNull()
        ?.get(key = secondThemeColor)
        ?: Color.Unspecified.toArgb()
    return Color(colorInt)
}


// FontSize -------------->>
suspend fun saveFontSize(context: Context, fontsize: String) {
    context.dataStore.edit { preferences ->
        preferences[fontSize] = fontsize
    }
}


suspend fun getInitialFontSize(context: Context): String {
    return context.dataStore.data.firstOrNull()?.get(fontSize) ?: "Normal"
}


suspend fun saveIdSet(
    context: Context,
    idSet: Set<Int>,
    preferencesString: String
) {
    val jsonString = Json.encodeToString(SetSerializer(Int.serializer()), idSet)
    val key = stringPreferencesKey(preferencesString)

    context.dataStore.edit { preferences ->
        preferences[key] = jsonString
    }
}

suspend fun getIdSet(
    context: Context,
    preferencesString: String
): Set<Int> {
    val key = stringPreferencesKey(preferencesString)

    val preferences = context.dataStore.data.first()
    val jsonString = preferences[key] ?: "[]"

    return Json.decodeFromString(SetSerializer(Int.serializer()), jsonString)
}

suspend fun saveLanguage(context: Context, newLocale: String) {
    context.dataStore.edit { preferences ->
        preferences[locale] = newLocale
    }
}

suspend fun getInitialLanguage(context: Context): String {
    return context.dataStore.data.firstOrNull()?.get(locale) ?: "404"
}
