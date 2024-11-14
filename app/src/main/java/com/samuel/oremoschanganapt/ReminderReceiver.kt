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

        // Reagendar o alarme para rodar novamente em 1 minuto
        scheduleNextAlarm(context)
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNextAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Define o alarme para ser acionado em 1 minuto
        val intervalMillis = 60 * 1000L
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + intervalMillis,
            pendingIntent
        )

        Log.d("ReminderReceiver", "Alarme agendado para daqui a 1 minuto")
    }
}
