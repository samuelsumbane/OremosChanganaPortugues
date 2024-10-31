package com.samuel.oremoschanganapt.view.sideBar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.samuel.oremoschanganapt.components.colorPickerDemo
import com.samuel.oremoschanganapt.functionsKotlin.stringToColor
import com.samuel.oremoschanganapt.R

// AppearanceWidget ============>
@Composable
fun AppearanceWidget(
    modeSetting: String,
    themeColorSetting: String,
): Pair<String, String>{
    var visibleAppearanceTab by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf(modeSetting) }
    var themeColor by remember { mutableStateOf(themeColorSetting) }

    Column(
        Modifier.fillMaxWidth(0.90f)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
            .background(Color.Transparent, RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { visibleAppearanceTab = !visibleAppearanceTab},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.grid_view_24),
                    contentDescription = "language icon"
                )
                Text(text = "Aparencia", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "go to languagepage"
                )
            }
        }

        if(visibleAppearanceTab){
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.onSecondary,
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomEnd = 10.dp,
                            bottomStart = 10.dp
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    Modifier
                        .fillMaxWidth()
//                        .background(
//                            MaterialTheme.colorScheme.onSecondary,
//                            RoundedCornerShape(10.dp)
//                        ),
                ) {
                    Row(Modifier.padding(start = 15.dp, top = 10.dp)) {
                        SidebarText( text = "Modo", fontSize = 15 )
                    }
                    mode = ModeSwitcher(mode)
                }
                Spacer(modifier = Modifier.height(10.dp))
                themeColor = RowColors(rowText = "Cor de tema", themeColor)

            }
        }
    }
    return Pair(mode, themeColor)
}



@Composable
fun ModeSwitcher(currentMode: String): String {
    var expanded by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf(currentMode) }
    val modes = listOf("Dark", "Light", "System")

    val modesTranslations = mapOf(
        "Dark" to "Escuro",
        "Light" to "Claro",
        "System" to "Sistema",
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(0.95f),
            shape = RoundedCornerShape(9.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                val currentModeName = modesTranslations[currentMode]!!
                Text(text = currentModeName)
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = true)
        ) {
            modes.forEach { thismode ->
                val modeName = modesTranslations[thismode]!!
                DropdownMenuItem(
                    text = { Text(text = modeName) },
                    onClick = {
                        mode = thismode
                        expanded = false
                    }
                )
            }
        }
    }
    return mode
}



@Composable
fun SidebarText(text: String, bold: Boolean = false, fontSize: Int = 16){
    Text(text = text, fontSize = fontSize.sp,
        fontWeight = if(bold) FontWeight.Bold else FontWeight.Normal,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun RowColors(
    rowText: String,
    defaultColor: String
): String{
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