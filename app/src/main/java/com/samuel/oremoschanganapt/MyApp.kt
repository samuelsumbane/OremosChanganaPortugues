package com.samuel.oremoschanganapt

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.debugInspectorInfo
import com.samuelsumbane.oremoschanganapt.db.Def
import com.samuelsumbane.oremoschanganapt.db.Pray
import com.samuelsumbane.oremoschanganapt.db.Reminder
import com.samuelsumbane.oremoschanganapt.db.Song
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp: Application() {

    companion object{
        lateinit var realm: Realm
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    Pray::class, Song::class,
                    Reminder::class, Def::class
                )
            )
        )

        val notificationChannel = NotificationChannel(
            "oremos_reminder",
            "oremos reminder",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.description = "A notificaiton service"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

    }
}