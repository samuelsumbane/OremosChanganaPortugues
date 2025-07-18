//package com.samuel.oremoschanganapt.db
//
//import android.os.Build
//import com.samuelsumbane.oremoschanganapt.db.Reminder
//
////package com.samuelsumbane.oremoschanganapt.db
//
//import android.util.Log
//import androidx.annotation.RequiresApi
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.samuel.oremoschanganapt.MyApp
////import com.samuelsumbane.oremoschanganapt.MyApp
//import io.realm.kotlin.UpdatePolicy
//import io.realm.kotlin.ext.query
//import io.realm.kotlin.query.Sort
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//
//class ReminderViewModel : ViewModel() {
//
//    private val realm = MyApp.realm
//
//    val reminders = realm
//        .query<Reminder>().asFlow().map { results -> results.list.toList() }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//
//
//    private fun getNextId(): Int {
//        val lastReminder = realm.query<Reminder>().sort("reminderId", Sort.DESCENDING).first().find()
//        return (lastReminder?.reminderId?: 0) + 1
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun addReminder(
//        reminderdata: Int, remindertable: String,
//        reminderdatetime: Long,
//        reminderrepeat: String
//    ) {
//        viewModelScope.launch {
//            realm.write {
//                val reminder = Reminder().apply {
//                    reminderId = getNextId()
//                    reminderData = reminderdata
//                    reminderTable = remindertable
//                    reminderDateTime = reminderdatetime
//                    reminderRepeat = reminderrepeat
//                }
//                copyToRealm(reminder, updatePolicy = UpdatePolicy.ALL)
//            }
//        }
//    }
//
//    fun updateReminder(reminderdatetime: Long, reminderid: Int? = null) {
//        viewModelScope.launch {
//           realm.write {
//                val foundReminder = this.query<Reminder>(query = "reminderId == $0", reminderid).find().firstOrNull()
//                try {
//                    foundReminder?.let {
//                        foundReminder.reminderDateTime = reminderdatetime
//
//                        copyToRealm(foundReminder, updatePolicy = UpdatePolicy.ALL)
//                    }
//                } catch (e: Exception){
//                    Log.d("Error deleting reminder", "${e.message}")
//                }
//           }
//        }
//    }
//
//    fun getReminderById(reminderId: Int): Reminder? {
//        return realm.query<Reminder>("reminderId == $0", reminderId).first().find()
//    }
//
//
//    fun deleteReminder(reminderid: Int){
//        viewModelScope.launch {
//            realm.write {
//                val reminder = this.query<Reminder>(query = "reminderId == $0", reminderid).find().firstOrNull()
//                try {
//                    reminder?.let { delete(it) }
//                } catch (e: Exception){
//                    Log.d("Error deleting reminder", "${e.message}")
//                }
//            }
//        }
//    }
//
//}