package com.samuel.oremoschanganapt.view.settingsPackage

//import com.samuel.oremoschanganapt.functionsKotlin.ColorPickerHSV
//import com.samuel.oremoschanganapt.getInitialThemeColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.colorSelectBox
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.saveSecondThemeColor
import com.samuel.oremoschanganapt.saveThemeColor
import com.samuel.oremoschanganapt.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearancePage(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_color), color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "go back")
                    }
                }
            )
        },
    ) { innerValues ->
        val itemBgColor = ColorObject.mainColor
        val scrollState = rememberScrollState()
//        var color by remember { mutableStateOf(Color.Red) }
        val context = LocalContext.current
        val themeColor by remember { mutableStateOf(ColorObject.mainColor) }
        val secondThemeColor by remember { mutableStateOf(ColorObject.secondColor) }
        var color by remember { mutableStateOf<Color>(themeColor) }
        var secondColor by remember { mutableStateOf<Color>(secondThemeColor) }
        var isSolidColorTabSelected by remember { mutableStateOf(false) }

        val colorList = listOf(
            listOf(Lightgray, Lightblue, Blue, BlueColor),
            listOf(Orange, Tomato, Red, RedButton),
            listOf(Pink, Purple, Turquoise, Green)
        )

        themeColor.let {
            Column(
                modifier = Modifier
                    .padding(innerValues)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(Modifier.height(60.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    for (eachList in colorList) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(0.8f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (color in eachList) {
                                colorSelectBox(color = color, selected = false) {
                                    coroutineScope.launch {
                                        saveThemeColor(context, color)
                                        ColorObject.mainColor = color
                                        //
                                        ColorObject.secondColor = Color.Unspecified
                                        saveSecondThemeColor(context, Color.Unspecified)

                                        toastAlert(context, text = "Nova cor foi definida com sucesso.")
                                    }
                                }
                            }
                        }
                    }

                    IconButton(
                        onClick = { navController.navigate("newColorPage") },
                        modifier = Modifier
                            .border(
                                width = 1.1.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                            .size(58.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add new color",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }






            }
        }
//        LoadingScreen()


    }

}
