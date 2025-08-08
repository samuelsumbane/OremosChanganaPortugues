package com.samuel.oremoschanganapt.view.morepagesPackage.remindersPages

import Reminder
import ReminderRepository
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.DatePickerModalInput
import com.samuel.oremoschanganapt.components.TimePickerDialog
import com.samuel.oremoschanganapt.components.buttons.NormalButton
import com.samuel.oremoschanganapt.components.buttons.CancelButton
import com.samuel.oremoschanganapt.components.buttons.submitButtonsRow
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuel.oremoschanganapt.functionsKotlin.combineTimestamps
import com.samuel.oremoschanganapt.functionsKotlin.convertLongToTimeString
import com.samuel.oremoschanganapt.functionsKotlin.convertTimePickerStateToLong
import com.samuel.oremoschanganapt.functionsKotlin.getCurrentTimestamp
import com.samuel.oremoschanganapt.functionsKotlin.convertLongToDateString
import com.samuel.oremoschanganapt.functionsKotlin.scheduleNotificationForSongOrPray
import com.samuel.oremoschanganapt.functionsKotlin.splitTimestamp
import com.samuel.oremoschanganapt.repository.ColorObject

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureReminder(
    navController: NavController,
    itemId: Int,
    table: String,
//    rdatetime: Long,
    rId: Long = 0L,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Definir lembrete", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    ) { paddingVales ->
        val context = LocalContext.current
        val reminderRepo = ReminderRepository(context)
        val reminders = reminderRepo.getAll()

        val possiblyReminder = reminders.firstOrNull { it.reminderData == itemId && it.reminderTable == table }

        var reminderdate by remember { mutableStateOf(getCurrentTimestamp()) }
        var remindertime by remember { mutableStateOf(0L) }
        possiblyReminder?.let {
            if (remindertime == 0L) {
                val (_, time) = splitTimestamp(it.reminderDateTime)
                remindertime = time
            }
        }

        val reminderrepeat = "no-repeat"

        var showDatePicker by remember { mutableStateOf(false)}
        var showTimePicker by remember { mutableStateOf(false)}
        val mainColor = ColorObject.mainColor
        var selectedTime: TimePickerState? by remember { mutableStateOf(null) }

        Column (
            Modifier
                .padding(paddingVales)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Row( Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

                DateTimeButtonLabel(text = convertLongToDateString(reminderdate)) {
                    showDatePicker = true
                }

                DateTimeButtonLabel(text = convertLongToTimeString(remindertime)) {
                    showTimePicker = true
                }
            }

            if (showTimePicker) {
                TimePickerDialog(
                    onDismiss = { showTimePicker = false },
                    onConfirm = { timePickerState ->
                        selectedTime = timePickerState
                        // Convert to Long ---------->
                        remindertime = convertTimePickerStateToLong(timePickerState)
                        showTimePicker = false
                    },
                    textColor = mainColor
                )
            }

            if (showDatePicker) {
                DatePickerModalInput(
                    onDateSelected = { timestamp ->
                        if (timestamp != null) {
                            reminderdate = timestamp
                        }
                        showDatePicker = false
                    }
                ) { showDatePicker = false }
            }

            fun editReminder(
                reminderId: Long = rId,
                reminderDateTime: Long
            ) {
                reminderRepo.update(
                    reminder = Reminder(
                        id = reminderId,
                        reminderData = itemId,
                        reminderTable = table,
                        reminderDateTime = reminderDateTime
                    )
                )
                toastAlert(context, "Lembrete actualizado com sucesso.")
            }

            submitButtonsRow {
                CancelButton(text = "Cancelar") { navController.popBackStack() }

                NormalButton("Finalizar") {
                    if (reminderdate == 0L ) {
                        toastAlert(context, "Por favor, selecione a data")
                    } else if (remindertime == 0L){
                        toastAlert(context, "Por favor, selecione a hora")
                    } else {
                        val reminderDateTime = combineTimestamps(reminderdate, remindertime)

                        if (rId != 0L){
                            // Edit reminder --------->>
                           editReminder(reminderDateTime = reminderDateTime)
                        } else {
                            // Create reminder ---------->>
                            if (possiblyReminder != null) {
                                editReminder(reminderId = possiblyReminder.id, reminderDateTime)
                            } else {
                                reminderRepo.insert(
                                    reminder = Reminder(
                                        reminderData = itemId,
                                        reminderTable = table,
                                        reminderDateTime = reminderDateTime
                                    )
                                )
                                toastAlert(context, "Lembrete adicionado com sucesso.")
                            }
                        }

                        scheduleNotificationForSongOrPray(context,
                            title = if (table == "Song") "Lembrete de dântico" else "Lembrete de oração",
                            message = if (table == "Song") "Eleve o seu espírito com este cântico." else "Fortaleça a sua fé com esta oração.",
                            timestamp = reminderDateTime
                        )
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun DateTimeButtonLabel(text: String, onClick: () -> Unit) {
    val color = MaterialTheme.colorScheme.tertiary
    Button(onClick) {
        Text(text, color = color, fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}