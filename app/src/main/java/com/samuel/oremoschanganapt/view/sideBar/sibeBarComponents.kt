package com.samuel.oremoschanganapt.view.sideBar

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.colorPickerDemo
import com.samuel.oremoschanganapt.functionsKotlin.stringToColor
import com.samuel.oremoschanganapt.R
//import com.samuel.oremoschanganapt.components.BackupPickerScreen
import com.samuel.oremoschanganapt.components.DefTabButton
import com.samuel.oremoschanganapt.components.RadioButtonDialog
//import com.samuel.oremoschanganapt.components.FilePickerScreen
import com.samuel.oremoschanganapt.components.buttons.ExpandContentTabBtn
import com.samuel.oremoschanganapt.components.KeyValueTextRow
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.saveThemeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//import com.samuel.oremoschanganapt.db.CommonViewModel

// AppearanceWidget --------->>
@Composable
fun AppearanceWidget(
    navController: NavController,
    modeSetting: String,
) {

    var visibleAppearanceTab by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf(modeSetting) }
    var showModeDialog by remember { mutableStateOf(false) }
    val modeOptions = mapOf(
        "Claro" to "Light",
        "Escuro" to "Dark",
        "Sistema" to "System"
    )

    var selectedModeOption by remember { mutableStateOf(mode) }
    val context = LocalContext.current

    DefTabButton {
        ExpandContentTabBtn(
            ImageVector.vectorResource(R.drawable.grid_view_24),
            "Aparência"
        ) { visibleAppearanceTab = !visibleAppearanceTab }

        AnimatedVisibility(visibleAppearanceTab){
            Column {
                Spacer(modifier = Modifier.height(10.dp))

                Column(Modifier.fillMaxWidth()) {
                    KeyValueTextRow(key = "Modo", value = modeOptions[modeSetting] ?: "Escuro") {
                        showModeDialog = true
                    }

                    Row (
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                            .clickable { navController.navigate("appearancePage") },
                        Arrangement.SpaceBetween
                    ) {
                        Text("Cor do app", fontSize = 17.sp)
                        Row(
                            Modifier
                                .size(24.dp)
                                .background(
                                    color = ColorObject.mainColor,
                                    shape = RoundedCornerShape(50)
                                )
                        ) {}
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))

            }

            if (showModeDialog) {
                RadioButtonDialog(
                    showDialog = showModeDialog,
                    title = "Modo",
                    options = modeOptions.keys.toList(),
                    selectedOption = selectedModeOption,
                    onOptionSelected = { option ->
                        selectedModeOption = option
                        showModeDialog = false
                        CoroutineScope(Dispatchers.IO).launch {
                            modeOptions[option]?.let {
                                saveThemeMode(context, it)
                            Log.d("themeMode", "thememode from save: $it")
                            }

                        }
                    },
                    onDismiss = { showModeDialog = false }
                )
            }

        }
    }
}



//@Composable
//fun ModeSwitcher(currentMode: String): String {
//    var expanded by remember { mutableStateOf(false) }
//    var mode by remember { mutableStateOf(currentMode) }
////    val modes = listOf("Dark", "Light", "System")
//
//    val modesTranslations = mapOf(
//        "Dark" to "Escuro",
//        "Light" to "Claro",
//        "System" to "Sistema",
//    )
//
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
////
//        Button(
//            onClick = { expanded = true },
//            modifier = Modifier.fillMaxWidth(0.95f),
//            shape = RoundedCornerShape(9.dp),
//            border = BorderStroke(1.dp, Color.Black),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Transparent,
//                contentColor = MaterialTheme.colorScheme.tertiary
//            ),
//        ) {
//            Row(
//                Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ){
//                val currentModeName = modesTranslations[currentMode]!!
//                Text(text = currentModeName)
//                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
//            }
//        }
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            properties = PopupProperties(focusable = true)
//        ) {
//            modes.forEach { thismode ->
//                val modeName = modesTranslations[thismode]!!
//                DropdownMenuItem(
//                    text = { Text(text = modeName) },
//                    onClick = {
//                        mode = thismode
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//    return mode
//}



@Composable
fun SidebarText(text: String, bold: Boolean = false, fontSize: Int = 16){
    Text(text = text, fontSize = fontSize.sp,
        fontWeight = if(bold) FontWeight.Bold else FontWeight.Normal,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun RowColors (
    rowText: String,
    defaultColor: String
): String {
    var returnValue by remember { mutableStateOf("") }
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Row(Modifier.fillMaxWidth(0.90f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            SidebarText(text = rowText, fontSize = 15)
            returnValue = colorPickerDemo(stringToColor(defaultColor), withIcon = false)
        }
    }
    return returnValue
}

@Composable
fun RowAbout (navController: NavController) {
    DefTabButton {
        ExpandContentTabBtn(
            Icons.Default.Info, "Sobre"
        ) { navController.navigate("about") }
    }
}


//@Composable
//fun RowBackup(commonViewModel: CommonViewModel) {
//    var isThisTabVisible by remember { mutableStateOf(false) }
//
//    DefTabButton {
//        ExpandContentTabBtn(
//            Icons.Default.Refresh,
//            "Backup / Restourar"
//        ) { isThisTabVisible = !isThisTabVisible }
//
//        AnimatedVisibility(isThisTabVisible) {
//            Column {
//                Text("Pode salvar (Backup) a lista de orações ou/e cânticos favoritos no dispositivo e carregar (Restourar) quando quiser actualizar a lista actual.", modifier = Modifier.padding(10.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth().padding(10.dp),
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    BackupPickerScreen(commonViewModel)
//                    FilePickerScreen(commonViewModel)
//                }
//            }
//        }
//    }
//}

