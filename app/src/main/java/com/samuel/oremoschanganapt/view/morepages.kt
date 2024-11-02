package com.samuel.oremoschanganapt.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.buttons.MorePagesBtn
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton

//import com.samuel.oremoschanganapt.functionsKotlin.showNotification

//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorePages(navController: NavController){
    Scaffold(
       topBar = {
           TopAppBar(
               title = { },
               navigationIcon = {
                   IconButton( onClick = { navController.popBackStack() } ) {
                      Icon(Icons.Default.ArrowBack, contentDescription = "back")
                   }
               }
           )
       }
    ) { innerPadding ->
        Column(
            Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//            showNotification("test", "esadk fsdf ljda l")
//            val channelId = "my_channel_id"
//            val name = "My Channel"
//            val descriptionText = "Description of My Channel"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, name, importance).apply {
//                description = descriptionText
//            }
//// Registrar o canal com o sistema; você não pode alterar a importância do canal ou outras notificações depois disso.
//            val notificationManager: NotificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)



            Column(
                Modifier.fillMaxWidth(0.80f)
//                    .background(Color.Green)
            ){
                val cS = 20 // cornerShape
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    MorePagesBtn("Apendice", Modifier.weight(1f)){
                        navController.navigate("apendice")
                    }

                    Spacer(Modifier.width(35.dp))

                    MorePagesBtn("Festas Moveis", Modifier.weight(1f)){
                        navController.navigate("festasmoveis")
                    }
                }

                Spacer(Modifier.height(35.dp))

                Row( Modifier.fillMaxWidth(), ) {

                    MorePagesBtn("Leccionário", Modifier.weight(1f)) {
                        navController.navigate("licionario")
                    }
                    Spacer(Modifier.width(35.dp))

                    MorePagesBtn("Favoritos", Modifier.weight(1f)) {
                        navController.navigate("favoritospage")
                    }
                }

                Spacer(Modifier.height(35.dp))

                Row(
                    Modifier.fillMaxWidth().height(85.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MorePagesBtn("Lembretes") {
                        navController.navigate("reminderspage")
                    }
                }


            }
        }

        ShortcutsButton(navController)

    }
}

