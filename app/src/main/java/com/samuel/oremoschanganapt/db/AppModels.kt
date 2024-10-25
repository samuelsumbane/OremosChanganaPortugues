package com.samuelsumbane.oremoschanganapt.db

//import androidx.room.PrimaryKey
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Pray: RealmObject {
    var title: String = ""
    var subTitle: String = ""
    var body: String = ""
    var loved: Boolean = false

    @PrimaryKey
    var prayId: Int = 0
}

class Song: RealmObject {
    var number: String = ""
    var group: String = ""
    var title: String = ""
    var subTitle: String = ""
    var body: String = ""
    var loved: Boolean  = false

    @PrimaryKey
    var songId: Int = 0
}

class Def: RealmObject {
    var textScale: Double = 1.0

    @PrimaryKey
    var defId: Int = 0
}