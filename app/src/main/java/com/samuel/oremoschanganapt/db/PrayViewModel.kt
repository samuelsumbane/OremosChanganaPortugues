package com.samuelsumbane.oremoschanganapt.db

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuel.oremoschanganapt.MyApp
//import com.samuelsumbane.oremoschanganapt.MyApp
import com.samuelsumbane.oremoschanganapt.db.data.prayData
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PrayViewModel : ViewModel() {

    private val realm = MyApp.realm

    val prays = realm
        .query<Pray>().asFlow().map { results -> results.list.toList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch {
            try {
                val existingPrays = realm.query<Pray>().find()
                if (existingPrays.isEmpty()) {
                    prayData.forEach {
                        addPray(it.title, it.subTitle, it.body, it.loved)
                    }
                }
            } catch (e: Exception){
                Log.d("PrayViewModel", "Error saving pray", e)
            }
        }
    }

    private fun getNextId(): Int {
        val lastNote = realm.query<Pray>().sort("prayId", Sort.DESCENDING).first().find()
        return (lastNote?.prayId ?: 0) + 1
    }

    fun addPray(
        praytitle: String,
        praysubTitle: String,
        praybody: String,
        prayloved: Boolean
    ) {
        viewModelScope.launch {
            realm.write {
                val pray = Pray().apply {
                    prayId = getNextId()
                    title = praytitle
                    subTitle = praysubTitle
                    body = praybody
                    loved = prayloved
                }
                copyToRealm(pray, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    fun updatePray(
        prayId: Int,
        prayloved: Boolean
    ){
        viewModelScope.launch {
            realm.write {
                val pray = this.query<Pray>("prayId == $0", prayId).find().first()
                pray.let {
                    pray.loved = prayloved
                }
                copyToRealm(pray, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    fun getPrayById(prayId: Int): Pray? {
        return realm.query<Pray>("prayId == $0", prayId).first().find()
    }


//    fun deleteNote(id: Int) {
//        viewModelScope.launch {
//            realm.write {
//                val note = this.query<Note>(query = "noteId == $0", id).find().firstOrNull()
//                try {
//                    note?.let {
////                       val noteuiid = note.uiId
//                        val todos = note.todos
//                        // Deleting this note todos -------->
//                        todos.forEach{todo ->
//                            delete(todo)
//                        }
//                        // Deleting note --------->
//                        delete(it)
//                    }
//                } catch (e: Exception) {
//                    Log.d("deleting note error", "${e.message}")
//                }
//            }
//        }
//    }

}