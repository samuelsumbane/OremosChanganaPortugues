package com.samuel.oremoschanganapt.view.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

object AppState {
    var configSongNumber by mutableIntStateOf(0)
}