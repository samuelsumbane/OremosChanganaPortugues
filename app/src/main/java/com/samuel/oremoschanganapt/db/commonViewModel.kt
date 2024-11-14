package com.samuelsumbane.oremoschanganapt.db

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuel.oremoschanganapt.MyApp
//import com.samuel.oremoschanganapt.db.data.songData
//import com.samuelsumbane.oremoschanganapt.MyApp
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CommonViewModel: ViewModel() {

        private val realm = MyApp.realm

        val lovedData = realm
            .query<LovedData>().asFlow().map { results -> results.list.toList() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())




        private fun getNextId(): Int {
            val lastItem = realm.query<LovedData>().sort("itemId", Sort.DESCENDING).first().find()
            return (lastItem?.lovedDataId ?: 0) + 1
        }

        fun getLovedItem(table: String, id: Int): LovedData? {
             return realm.query<LovedData>(query = "lovedDataId == $0 && tableName == $1", id, table).find().firstOrNull()
        }

        fun addLovedId(table: String, id: Int )  {

            viewModelScope.launch {
                when(table) {
                    "Pray", "Song" -> {
                        realm.write {
                            val tableData = LovedData().apply {
                                itemId = getNextId()
                                tableName = table
                                lovedDataId = id
                            }
                            copyToRealm(tableData, updatePolicy = UpdatePolicy.ALL)
                        }
                    }
                }
            }
        }

        fun removeLovedId(idTable: String, id: Int ) {
            viewModelScope.launch {
                realm.write {
                    try {
                        val itemData = this.query<LovedData>(query = "lovedDataId == $0 && tableName == $1", id, idTable).find().firstOrNull()
                        itemData?.let {
                            delete(it)
                        }
                    } catch (ex: Exception) {
                        Log.d("ExceptionError", "${ex.message}")
                    }
                }
            }
        }


        fun getSongById(songId: Int): Song? {
            return realm.query<Song>("songId == $0", songId).first().find()
        }


}