package com.samuel.oremoschanganapt

import android.app.Application
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

    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    Pray::class,
                    Song::class,
                    Reminder::class,
                    Def::class
                )
            )
        )
    }

}