package com.samuel.oremoschanganapt.view.morepagesPackage.remindersPages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.TextIconRow
import com.samuel.oremoschanganapt.components.buttons.NormalButton
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.functionsKotlin.convertLongToTimeString
import com.samuel.oremoschanganapt.functionsKotlin.convertLongToDateString
import com.samuel.oremoschanganapt.functionsKotlin.splitTimestamp
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.DarkColor
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
) {
    val allReminders by reminderViewModel.reminders.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Lembretes", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick={  navController.popBackStack()  } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }) { paddingValues ->

        val mainColor = colorObject.mainColor
        val textColor = Color.White
        val context = LocalContext.current

        if (allReminders.isEmpty()) {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Nenhum lembrete encontrado.", color = textColor, fontWeight = FontWeight.SemiBold)
            }
        } else {
            LazyColumn(
                Modifier.fillMaxSize().padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(allReminders){ reminder ->
                    fun seeReminderContent(){
                        if (reminder.reminderTable == "Pray"){
                            navController.navigate("eachOracao/${reminder.reminderData}")
                        } else {
                            navController.navigate("eachCantico/${reminder.reminderData}")
                        }
                    }

                    val (reminderdate, remindertime) = splitTimestamp(reminder.reminderDateTime)

                    val reminderTitleValue by remember { mutableStateOf(if (reminder.reminderTable == "Pray") {
                        "Oração: ${prayViewModel.getPrayById(reminder.reminderData)?.title!!}"
                    } else {
                        "Cântico: ${songViewModel.getSongById(reminder.reminderData)?.number!!} - ${songViewModel.getSongById(reminder.reminderData)?.title!!}"
                    }) }

                    var reminderTitle by remember { mutableStateOf(reminderTitleValue) }
                    var reminderTitleText by remember { mutableStateOf("") }

                    Column(
                        Modifier.fillMaxWidth(0.95f).background(colorObject.mainColor, RoundedCornerShape(10.dp)),
                    ) {
                        var showDetails by remember { mutableStateOf(false) }

                        fun showContent() {
                            if (showDetails) {
                                reminderTitle = reminderTitleValue
                                reminderTitleText = ""
                            } else {
                                reminderTitle = ""
                                reminderTitleText = reminderTitleValue
                            }
                            showDetails = !showDetails
                        }

                        TextIconRow(reminderTitle, showDetails, modifier = Modifier.clickable { showContent() })

                        AnimatedVisibility(showDetails){
                            Column(
                                Modifier.fillMaxWidth().background(brush = Brush.horizontalGradient(
                                    colors = listOf(mainColor, lerp(mainColor, DarkColor, 0.9f)
                                    )), RoundedCornerShape(0.dp, 0.dp, 14.dp, 14.dp))
                                    .clickable { seeReminderContent() }

                            ) {
                                Row(Modifier.padding(10.dp)) {
                                    Text(reminderTitleText, color = textColor, softWrap = true)
                                }

                                Spacer(Modifier.height(15.dp))

                                Row(modifier = Modifier.padding(10.dp)) {
                                    Text("${convertLongToDateString(reminderdate)}  |", color = textColor)
                                    Text(" ${convertLongToTimeString(remindertime)}", color = textColor)
                                }

                                Spacer(Modifier.height(15.dp))

                                Row (
                                    Modifier.fillMaxWidth(0.9f)
                                        .align(Alignment.CenterHorizontally),
                                    horizontalArrangement = Arrangement.SpaceAround){

                                    NormalButton("Ver", MaterialTheme.colorScheme.tertiary) {
                                        seeReminderContent()
                                    }

                                    NormalButton("Editar", MaterialTheme.colorScheme.tertiary){
                                        val r = reminder
                                        navController.navigate("configurereminder/${r.reminderData}/${r.reminderTable}/${r.reminderDateTime}/${r.reminderId}")
                                    }

                                    NormalButton("Remover", RedButton) {
                                        reminderViewModel.deleteReminder(reminder.reminderId)
                                        toastAlert(context, "Lembrente removido com sucesso.")
                                    }
                                }
                                Spacer(Modifier.height(15.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(15.dp))
                }
            }
        }

        ShortcutsButton(navController)
    }
}
