package com.samuel.oremoschanganapt.db

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
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

    fun exportBackupToExternalStorage(context: Context) {
        try {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Files.FileColumns.DISPLAY_NAME, "lovedItems_backup.json")
                put(MediaStore.Files.FileColumns.MIME_TYPE, "application/json")
                put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/OremosChanganapt_backup")
            }

            // Select "loved" itens in tables Pray and Song -------->>
            val lovedPrays = realm.query<Pray>("loved = true").find()
            val lovedSongs = realm.query<Song>("loved = true").find()

            // Mapear os dados para o formato JSON
            val lovedDataList = mutableListOf<LovedDataPojo>()
            lovedPrays.forEach { lovedDataList.add(LovedDataPojo(it.prayId, "Pray")) }
            lovedSongs.forEach { lovedDataList.add(LovedDataPojo(it.songId, "Song")) }

            if (lovedDataList.isEmpty()) {
                toastAlert(context, "Nenhum dado encontrado para exportar.")
                return
            }

            val json = Gson().toJson(lovedDataList)

            // Verificar e substituir arquivo existente
            val queryUri = MediaStore.Files.getContentUri("external")
            val selection = "${MediaStore.Files.FileColumns.RELATIVE_PATH} = ? AND ${MediaStore.Files.FileColumns.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(
                Environment.DIRECTORY_DOCUMENTS + "/OremosChanganapt_backup/",
                "lovedItems_backup.json"
            )

            resolver.query(queryUri, null, selection, selectionArgs, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                    val deleteUri = ContentUris.withAppendedId(queryUri, id)
                    resolver.delete(deleteUri, null, null)
                }
            }

            // Save new backup ------>>
            val uri = resolver.insert(queryUri, contentValues)
            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.write(json.toByteArray())
                    toastAlert(context, "Backup salvo com sucesso!")
                }
            } ?: run {
                toastAlert(context, "Erro ao criar backup.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            toastAlert(context, "Falha ao salvar backup: ${e.message}")
        }
    }

    fun restoreBackupFromExternalStorage(context: Context) {
        try {
            val backupFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "OremosChanganapt_backup/lovedItems_backup.json"
            )

            if (!backupFile.exists()) {
                toastAlert(context, "Arquivo de backup não encontrado.")
                return
            }

            val json = backupFile.readText()
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
            toastAlert(context, "Backup restaurado com sucesso.")
        } catch (e: Exception) {
            e.printStackTrace()
            toastAlert(context, "Falha ao restaurar backup: ${e.message}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

}