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

class DefViewModel : ViewModel() {

    private val realm = MyApp.realm

    val defs = realm
        .query<Def>().asFlow().map { results -> results.list.toList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch {
            try {
                val existingDef = realm.query<Def>().find()
                if (existingDef.isEmpty()) {
                    viewModelScope.launch {
                        realm.write {
                            val def = Def().apply {
                                defId = getNextId()
                                textScale = 1.0
                            }
                            copyToRealm(def, updatePolicy = UpdatePolicy.ALL)
                        }

                        Log.d("Salvo", "Dados salvos com sucess")
                    }
                }


            } catch (e: Exception){
                Log.d("DefViewModel", "Error saving Def", e)
            }
        }
    }

    private fun getNextId(): Int {
        val lastDef = realm.query<Def>().sort("defId", Sort.DESCENDING).first().find()
        return (lastDef?.defId ?: 0) + 1
    }

//    fun addPray(
//        praytitle: String,
//        defsubTitle: String,
//        praybody: String,
//        prayloved: Boolean
//    ) {
//        viewModelScope.launch {
//            realm.write {
//                val pray = Pray().apply {
//                    prayId = getNextId()
//                    title = praytitle
//                    subTitle = defsubTitle
//                    body = praybody
//                    loved = prayloved
//                }
//                copyToRealm(pray, updatePolicy = UpdatePolicy.ALL)
//            }
//        }
//    }
//
    fun updateDef(
        textScale: Double
    ){
        viewModelScope.launch {
            realm.write {
                val def = this.query<Def>("defId == $0", 1).find().first()
                def.let {
                    def.textScale = textScale
                }
                copyToRealm(def, updatePolicy = UpdatePolicy.ALL)
            }
        }
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