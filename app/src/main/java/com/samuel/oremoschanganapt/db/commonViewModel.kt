package com.samuel.oremoschanganapt.db

import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.gson.Gson
import com.samuel.oremoschanganapt.MyApp
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuelsumbane.oremoschanganapt.db.Pray
import com.samuelsumbane.oremoschanganapt.db.Song
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import java.io.File

// POJO class to exporting ---------->>
data class LovedDataPojo(
    val lovedDataId: Int,
    val tableName: String
)

class CommonViewModel: ViewModel() {

    private val realm = MyApp.realm

    fun getLovedPrays(): List<Pray> {
        return realm.query<Pray>("loved = true").find()
    }

    fun getLovedSongs(): List<Song> {
        return realm.query<Song>("loved = true").find()
    }

    fun restoreBackup(json: String, context: Context) {
        try {
            val lovedList: List<LovedDataPojo> = Gson().fromJson(json, Array<LovedDataPojo>::class.java).toList()

            realm.writeBlocking {
                lovedList.forEach { lovedItem ->
                    when (lovedItem.tableName) {
                        "Pray" -> {
                            val pray = query<Pray>("prayId = $0", lovedItem.lovedDataId).first().find()
                            pray?.apply { loved = true }?.let { copyToRealm(it, updatePolicy = UpdatePolicy.ALL) }
                        }
                        "Song" -> {
                            val song = query<Song>("songId = $0", lovedItem.lovedDataId).first().find()
                            song?.apply { loved = true }?.let { copyToRealm(it, updatePolicy = UpdatePolicy.ALL) }
                        }
                    }
                }
            }
//            toastAlert(,"Dados restourados com sucesso.")
            toastAlert(context, "Dados restourados com sucesso.")

        } catch (e: Exception) {
            e.printStackTrace()
            // Tratar erro ou exibir mensagem ao usuário
        }
    }


    override fun onCleared() {
        super.onCleared()
        realm.close()
    }
}
