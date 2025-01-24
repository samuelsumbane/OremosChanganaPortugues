package com.samuelsumbane.oremoschanganapt.db

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
//import com.google.gson.Gson
import com.samuel.oremoschanganapt.MyApp
import com.samuel.oremoschanganapt.components.toastAlert
//import com.samuel.oremoschanganapt.db.data.songData
//import com.samuelsumbane.oremoschanganapt.MyApp
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

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


//        fun getSongById(songId: Int): Song? {
//            return realm.query<Song>("songId == $0", songId).first().find()
//        }



    // POJO class to exporting ---------->>
    data class LovedDataPojo(
        val tableName: String,
        val lovedDataId: Int,
        val itemId: Int
    )

    fun exportBackupToExternalStorage(context: Context) {
        // Select Realm data ------->>
        val loved = realm.query<LovedData>().find()

        if (loved.isEmpty()) {
            toastAlert(context, "Nenhum dado encontrado para exportar.")
            return
        }

        // Map Realm data to POJO class -------->>
        val lovedPojo = loved.map {
            LovedDataPojo(
                tableName = it.tableName,
                lovedDataId = it.lovedDataId,
                itemId = it.itemId
            )
        }

        // Serialize the Json data --------->>
        val json = Gson().toJson(lovedPojo)

        // Define the dir to save backup --------->>
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "OremosChanganapt_backup")
        if (!directory.exists()) {
            directory.mkdirs() // Create a dir, if needed --------->>
        }

        val backupFile = File(directory, "lovedItems_backup.json")
        backupFile.writeText(json)

        toastAlert(context, "Backup salvo em: ${backupFile.absolutePath}")
    }

    fun restoreBackupFromExternalStorage(context: Context) {
        val backupFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "OremosChanganapt_backup/lovedItems_backup.json")

        if (!backupFile.exists()) {
            toastAlert(context, "Backup não encontrado.")
            return
        }

        val json = backupFile.readText()
        val lovedList: List<LovedDataPojo> = Gson().fromJson(json, Array<LovedDataPojo>::class.java).toList()

        // Mapping to a structure of Realm ------->>
        val lovedRealmList = lovedList.map {
            LovedData().apply {
                tableName = it.tableName
                lovedDataId = it.lovedDataId
                itemId = it.itemId
            }
        }

        // Inserting or updating on Realm ---------->>
        realm.writeBlocking {
            lovedRealmList.forEach {
                copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
            }
        }

        toastAlert(context, "Backup restaurado com sucesso.")
    }




    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

}