package com.samuelsumbane.oremoschanganapt.db

//import androidx.room.PrimaryKey
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


// Prays table ---------->>
class Pray: RealmObject {
    var title: String = ""
    var subTitle: String = ""
    var body: String = ""

    @PrimaryKey
    var prayId: Int = 0
}

// Song table --------->>
class Song: RealmObject {
    var number: String = ""
    var group: String = ""
    var title: String = ""
    var subTitle: String = ""
    var body: String = ""

    @PrimaryKey
    var songId: Int = 0
}


// LovedData table ---------->>
class LovedData: RealmObject {
    var tableName: String = ""
    var lovedDataId: Int = 0

    @PrimaryKey
    var itemId: Int = 0

}

class Reminder: RealmObject {
    var reminderData: Int = 0
    var reminderTable: String = ""
    var reminderDate: Long? = null
    var reminderTime: Long? = null
    var reminderRepeat: String = ""

    @PrimaryKey
    var reminderId: Int = 0
}


class Def: RealmObject {
    var textScale: Double = 1.0
    var themeColor: String = "Tomato"
    var appMode: String = "System"

    @PrimaryKey
    var defId: Int = 0
}


