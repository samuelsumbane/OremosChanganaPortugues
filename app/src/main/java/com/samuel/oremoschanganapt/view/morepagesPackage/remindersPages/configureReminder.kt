package com.samuel.oremoschanganapt.view.morepagesPackage.remindersPages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.DatePickerModalInput
import com.samuel.oremoschanganapt.components.TimePickerDialog
import com.samuel.oremoschanganapt.components.buttons.NormalButton
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuel.oremoschanganapt.functionsKotlin.combineTimestamps
import com.samuel.oremoschanganapt.functionsKotlin.convertLongToTimeString
import com.samuel.oremoschanganapt.functionsKotlin.convertTimePickerStateToLong
import com.samuel.oremoschanganapt.functionsKotlin.getCurrentTimestamp
import com.samuel.oremoschanganapt.functionsKotlin.convertLongToDateString
import com.samuel.oremoschanganapt.functionsKotlin.scheduleNotificationForSongOrPray
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.BlueButton
import com.samuel.oremoschanganapt.ui.theme.RedButton

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureReminder(
    navController: NavController,
    id: Int, table: String,
    rdatetime: Long,
    rId: Int? = null,
    reminderViewModel: ReminderViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Definir lembrete", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={ navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    ) { paddingVales ->

        var reminderdate by remember { mutableStateOf(getCurrentTimestamp()) }
        var remindertime by remember { mutableStateOf(0L) }

        val reminderrepeat = "no-repeat"
        val context = LocalContext.current

        var showDatePicker by remember { mutableStateOf(false)}
        var showTimePicker by remember { mutableStateOf(false)}
        val mainColor = colorObject.mainColor
        var selectedTime: TimePickerState? by remember { mutableStateOf(null) }

        Column (
            Modifier
                .fillMaxSize()
                .padding(paddingVales)
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
                    onDateSelected = {timestamp ->
                        if (timestamp != null) {
                            reminderdate = timestamp
                        }
                        showDatePicker = false
                    }
                ) { showDatePicker = false }
            }

            Row(
                Modifier
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceAround
            ){

                NormalButton("Cancelar", RedButton) { navController.popBackStack() }

                NormalButton("Finalizar") {
                    if (reminderdate == 0L ) {
                        toastAlert(context, "Por favor, selecione a data")
                    } else if (remindertime == 0L){
                        toastAlert(context, "Por favor, selecione a hora")
                    } else {
                        val reminderDateTime = combineTimestamps(reminderdate, remindertime)

                        if (rId != 0){
                            // Edit reminder --------->>
                            reminderViewModel.updateReminder( reminderDateTime, rId)
                            toastAlert(context, "Lembrete actualizado com sucesso.")

                        } else {
                            // Create reminder ---------->>
                            reminderViewModel.addReminder(
                                id, table,
                                reminderDateTime,
                                reminderrepeat
                            )
                            toastAlert(context, "Lembrete adicionado com sucesso.")
                        }

//                        val title = when(table) {
//                            "Pray" -> "Oração"
//                            "Songs" -> "Cântico"
//                            else -> ""
//                        }

//                        scheduleNotificationForSongOrPray(context, title, "bava ha hin", reminderDateTime)

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
    TextButton(onClick) {
        Text(text, color = color, fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}