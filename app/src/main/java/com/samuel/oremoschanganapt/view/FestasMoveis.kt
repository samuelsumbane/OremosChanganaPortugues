package com.samuel.oremoschanganapt.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.repository.colorObject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestasMoveis(navController: NavController){


    val scroll = rememberScrollState()

    val datas = listOf(
        arrayOf("2024", "B", "14/Fev", "31/Mar", "19/Maio", "02/Junh", "01/Dez"),
        arrayOf("2025", "C", "05/Mar", "20/Abril", "08/Junho", "22/Junh", "30/Nov"),
        arrayOf("2026", "A", "18/Fev", "05/Abril", "24/Maio", "07/Junh", "29/Nov"),
        arrayOf("2027", "B", "10/Fev", "28/Mar", "16/Maio", "30/Maio", "28/Nov"),
        arrayOf("2028", "C", "02/Mar", "16/Abril", "04/Junho", "18/Junh", "03/Dez"),
        arrayOf("2029", "A", "14/Fev", "01/Abril", "20/Maio", "03/Junh", "02/Dez"),
        arrayOf("2030", "B", "06/Mar", "21/Abril", "09/Junho", "23/Junh", "01/Dez"),
        arrayOf("2031", "C", "26/Fev", "13/Abril", "01/Junho", "15/Junh", "30/Nov"),
        arrayOf("2032", "A", "11/Fev", "28/Marc", "16/Maio", "30/Maio", "28/Nov"),
        arrayOf("2033", "B", "02/Mar", "17/Abril", "05/Junho", "19/Junh", "27/Nov"),
        arrayOf("2034", "C", "22/Fev", "09/Abril", "28/Maio", "11/Junh", "03/Dez"),
        arrayOf("2035", "A", "07/Fev", "25/Mar", "13/Maio", "27/Maio", "02/Dez"),
        arrayOf("2036", "B", "27/Fev", "13/Abril", "01/Junho", "15/Junh", "30/Nov"),
        arrayOf("2037", "C", "18/Fev", "05/Abril", "24/Maio", "07/Junh", "29/Nov"),
        arrayOf("2038", "A", "10/Mar", "25/Abril", "13/Junho", "27/Junh", "28/Nov")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Festas Moveis", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "go back")
                    }
                }
            )
        },
    ) { innerValues ->
        val dbBgColor = colorObject.mainColor
        val divBgColor = lerp(dbBgColor, Color.Black, 0.2f)
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerValues)
//                .background(Color.Green)
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            datas.forEach{ row ->
                Column(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .background(divBgColor, RoundedCornerShape(10.dp))
                ){
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
//                            .background(Color.Blue)
                    ){
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ){
                            Text(text = row[0], fontWeight = FontWeight.Bold)
                            Text(text = row[1], fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Cinzas :  ----------------------------------- ${row[2]}")
                        Text(text = "Pascoa :  ---------------------------------- ${row[3]}")
                        Text(text = "Pentecostes :  -------------------------- ${row[4]}")
                        Text(text = "C. Cristo :  -------------------------------- ${row[5]}")
                        Text(text = "Advento :  --------------------------------- ${row[6]}")
                    }

                }
                Spacer(Modifier.height(10.dp))
            }
        }
        ShortcutsButton(navController)
    }
}
