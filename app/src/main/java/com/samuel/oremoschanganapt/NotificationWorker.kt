package com.samuel.oremoschanganapt


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.realm.kotlin.Realm

import android.content.BroadcastReceiver
//import android.content.Context
import android.content.Intent
import android.util.Log




//class NotificationWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
////    @Composable
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun doWork(): Result {
//
////        val realm = Realm.getDef
//
//        // Chamando seu serviço de notificação
//        val title = inputData.getString("title") ?: ""
//        val text = inputData.getString("text") ?: ""
//
//        val appNotificationService = AppNotificationService(applicationContext, title, text)
//
////        appNotificationService.showBasicNotification()
//        appNotificationService.showBasicNotication()
//        return Result.success()
//    }
//}
