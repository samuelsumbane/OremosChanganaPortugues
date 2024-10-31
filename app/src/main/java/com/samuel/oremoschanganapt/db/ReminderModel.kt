package com.samuel.oremoschanganapt.db

import android.os.Build
import com.samuelsumbane.oremoschanganapt.db.Reminder

//package com.samuelsumbane.oremoschanganapt.db

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuel.oremoschanganapt.MyApp
import com.samuel.oremoschanganapt.functionsKotlin.americanFormat
import com.samuel.oremoschanganapt.functionsKotlin.localTime
//import com.samuelsumbane.oremoschanganapt.MyApp
import com.samuelsumbane.oremoschanganapt.db.data.prayData
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReminderViewModel : ViewModel() {

    private val realm = MyApp.realm

    val reminders = realm
        .query<Reminder>().asFlow().map { results -> results.list.toList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private fun getNextId(): Int {
        val lastReminder = realm.query<Reminder>().sort("reminderId", Sort.DESCENDING).first().find()
        return (lastReminder?.reminderId?: 0) + 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addReminder(
        reminderdata: Int, remindertable: String,
        reminderrepeat: String
    ) {
        viewModelScope.launch {
            realm.write {
                val reminder = Reminder().apply{
                    reminderId = getNextId()
                    reminderData = reminderdata
                    reminderTable = remindertable
                    reminderDate = americanFormat()
                    reminderTime = localTime()
                    reminderRepeat = reminderrepeat
                }
                copyToRealm(reminder, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

//    fun updateReminder(
//        prayId: Int,
//        prayloved: Boolean
//    ){
//        viewModelScope.launch {
//            realm.write {
//                val pray = this.query<Reminder>("prayId == $0", prayId).find().first()
//                pray.let {
//                    pray.loved = prayloved
//                }
//                copyToRealm(pray, updatePolicy = UpdatePolicy.ALL)
//            }
//        }
//    }

    fun getReminderById(reminderId: Int): Reminder? {
        return realm.query<Reminder>("reminderId == $0", reminderId).first().find()
    }

    fun deleteReminder(reminderid: Int){
        viewModelScope.launch {
            realm.write {
                val reminder = this.query<Reminder>(query = "reminderId == $0", reminderid).find().firstOrNull()
                try {
                    reminder?.let {
                        delete(it)
                    }
                } catch (e: Exception){
                    Log.d("Error deleting reminder", "${e.message}")
                }
            }
        }
    }

}