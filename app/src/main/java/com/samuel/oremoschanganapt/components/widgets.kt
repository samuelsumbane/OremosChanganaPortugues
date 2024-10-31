package com.samuel.oremoschanganapt.components

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable


fun toastAlert(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT){
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(context, text, duration)
    toast.show()
}


@Composable
fun addReminder(
    onClick: () -> Unit
){
    IconButton(
        onClick = onClick
    ) {
        Icon(Icons.Default.Info, contentDescription = "Reminder")
    }
}

//@Composable
//fun reminder(
//    onClick: () -> Unit
//){
//    IconButton(
//        onClick = onClick
//    ) {
//        Icon(Icons.Default.Info, contentDescription = "Reminder")
//    }
//}