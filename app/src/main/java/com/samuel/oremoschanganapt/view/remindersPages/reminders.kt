package com.samuel.oremoschanganapt.view.remindersPages

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.AppNotificationService
import com.samuel.oremoschanganapt.components.buttons.NormalButton
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.functionsKotlin.convertLongToTimeString
import com.samuel.oremoschanganapt.functionsKotlin.longToRealDate
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.BlueButton
import com.samuel.oremoschanganapt.ui.theme.RedButton
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel
import androidx.compose.foundation.layout.Column as Column

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersPage(navController: NavController,
                  songViewModel: SongViewModel,
                  prayViewModel: PrayViewModel,
                  reminderViewModel: ReminderViewModel
){

    val allReminders by reminderViewModel.reminders.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Lembretes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick={  navController.popBackStack()  } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },

        ){ paddingValues ->
        val textColor = MaterialTheme.colorScheme.onPrimary
        val context = LocalContext.current


            LazyColumn(
                Modifier.fillMaxSize().padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                items( allReminders ){ reminder ->

                    fun seeReminderContent(){
                        if (reminder.reminderTable == "Pray"){
                            navController.navigate("eachOracao/${reminder.reminderData}")
                        } else {
                            navController.navigate("eachCantico/${reminder.reminderData}")
                        }
                    }

                    val remindertitle = if (reminder.reminderTable == "Pray") {
                        prayViewModel.getPrayById(reminder.reminderData)?.title!!
                    } else {
                        songViewModel.getSongById(reminder.reminderData)?.title!!
                    }

                    Column(
                        Modifier.background(colorObject.mainColor, RoundedCornerShape(10.dp)),
                    ) {
                        var showDetails by remember { mutableStateOf(false) }

                        Row(
                            Modifier.fillMaxWidth(0.95f).height(40.dp).clickable {
                                showDetails = !showDetails
                            },
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(remindertitle, color = textColor, softWrap = false,
                                modifier = Modifier.fillMaxWidth(0.70f))

                            IconButton( onClick = { showDetails = !showDetails } ) {
                                if (showDetails) {
                                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Close")
                                } else {
                                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Expand")
                                }
                            }
                        }

                        if (showDetails){
                            Column(
                                Modifier.fillMaxWidth(0.95f).clickable {
                                    seeReminderContent()
                                }
                            ) {
                                Text("$remindertitle", modifier = Modifier.padding(10.dp))

                                Spacer(Modifier.height(15.dp))

                                Row(modifier = Modifier.padding(10.dp)) {
                                    Text("${longToRealDate(reminder.reminderDate!!)}  |", color = textColor)
                                    Text(" ${convertLongToTimeString(reminder.reminderTime!!)}", color = textColor)
                                }

                                Spacer(Modifier.height(15.dp))

                                Row (
                                    Modifier.fillMaxWidth(0.9f)
                                        .align(Alignment.CenterHorizontally),
                                    horizontalArrangement = Arrangement.SpaceAround){

                                    NormalButton("Ver", Color.White, hasBorder = true, borderWidth = 2 ) {
                                        seeReminderContent()
                                    }

                                    NormalButton("Editar", BlueButton, hasBorder = true, borderWidth = 2 ){
                                        val r = reminder
                                        navController.navigate("configurereminder/${r.reminderData}/" +
                                                "${r.reminderTable}/${r.reminderDate}/${r.reminderTime}/${r.reminderId}")
                                    }

                                    NormalButton("Deletar", RedButton, hasBorder = true, borderWidth = 2 ) {
                                        reminderViewModel.deleteReminder(reminder.reminderId)
                                        toastAlert(context, "Lembrente deletado com sucesso.")
                                    }
                                }
                                Spacer(Modifier.height(15.dp))
                            }
                        }
                    }

                    Spacer(Modifier.height(15.dp))
                }
            }

            ShortcutsButton(navController)
    }
}

