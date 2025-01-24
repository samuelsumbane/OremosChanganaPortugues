package com.samuel.oremoschanganapt

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class ReminderReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        // Obtém os extras do Intent
        val title = intent.getStringExtra("NOTIFICATION_TITLE")
        val message = intent.getStringExtra("NOTIFICATION_MESSAGE")

        if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
            val appNotificationService = AppNotificationService(context, title, message)
            appNotificationService.showBasicNotication()
        }

        Log.d("ReminderReceiver", "Notificação enviada: $title | $message")

    }
}
