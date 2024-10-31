package com.samuel.oremoschanganapt.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.functionsKotlin.ShareIconButton
import com.samuelsumbane.oremoschanganapt.db.DefViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.buttons.StarButton
import com.samuel.oremoschanganapt.db.ReminderViewModel
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachOracao(navController: NavController, prayId: Int,
               prayViewModel: PrayViewModel,
               defViewModel: DefViewModel,
               reminderViewModel: ReminderViewModel
){

    val allDef by defViewModel.defs.collectAsState()
    val prayData = prayViewModel.getPrayById(prayId)

    if (prayData != null){
        var lovedPray by remember { mutableStateOf(prayData.loved) }

        val reminderes by reminderViewModel.reminders.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text="Oração") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    navigationIcon = {
                        IconButton(onClick={  navController.popBackStack()  } ){
                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    actions = {
                        val context = LocalContext.current

                        var hasReminder = false
                        Log.d("pray", "$prayId")

                        if (reminderes.isNotEmpty()){
                            for(reminder in reminderes){
                                if (reminder.reminderTable == "Pray"){
                                    if (reminder.reminderId == prayId){
                                        hasReminder = true
                                    }
                                }
                            }
                        } else { hasReminder = false }

                        if (hasReminder) {
                            IconButton(
                                // by now the fun is delete reminder ----->>
                                onClick = {
                                    reminderViewModel.deleteReminder(prayId)
                                }
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "reminder set")
                            }
                        } else {
                            IconButton(
                                // by now fun is add reminder ------>>
                                onClick = {
                                    reminderViewModel.addReminder(
                                        reminderdata = prayId,
                                        remindertable = "Pray",
                                        reminderrepeat = "No repeat"
                                    )
                                }
                            ) {
                                Icon(Icons.Default.Call, contentDescription = "no reminder set")
                            }
                        }

                        // The star icon ------>>
                        lovedPray = StarButton(
                            itemLoved = lovedPray,
                            prayViewModel = prayViewModel,
                            songViewModel = null,
                            id = prayData.prayId,
                            view = "prayViewModel"
                        )

                        ShareIconButton(context,  text = "${prayData.title} \n ${prayData.body}")
                    }
                )
            },

            ){paddingValues ->
            val scrollState = rememberScrollState()

            if(allDef.isNotEmpty()){

                val def = allDef.first()
                val dbScale by  remember { mutableDoubleStateOf(def.textScale) }
                var scale by remember { mutableFloatStateOf(dbScale.toFloat()) }

                Box(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newScale = scale * zoom
                        scale = newScale.coerceIn(1.0f, 2.0f) // intervalo do zoom.
                        defViewModel.updateDef("textScale", scale.toDouble())
                    }
                }) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(paddingValues)
                            .verticalScroll(scrollState)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = prayData.title.uppercase(), fontWeight = FontWeight.Bold, fontSize = 17.sp * scale, lineHeight = (24.sp * scale))
                        Spacer(modifier = Modifier.height(12.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = prayData.body,
                            modifier = Modifier.fillMaxWidth().padding(14.dp, 0.dp, 14.dp, 0.dp),
                            textAlign = TextAlign.Justify,
                            fontSize = 19.sp * scale, lineHeight = (24.sp * scale)
                        )
                    }
                }

                ShortcutsButton(navController)

            }
        }

    } else {
        Text("Carregando oracao...")
    }

}
