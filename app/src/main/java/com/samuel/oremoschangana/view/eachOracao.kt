package com.samuel.oremoschangana.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschangana.components.BottomAppBarPrincipal
import com.samuel.oremoschangana.components.InputPesquisa
import com.samuel.oremoschangana.functionsKotlin.ShareIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachOracao(navController: NavController, titulo:String, corpo: String){

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Oração", color = MaterialTheme.colorScheme.primary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={  navController.popBackStack()  } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    val context = LocalContext.current
                    ShareIconButton(context,  text = "$titulo \n $corpo")
                }
            )
        },

        ){paddingValues ->
        val scrollState = rememberScrollState()

        Box(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = titulo.uppercase(), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = corpo,
                    modifier = Modifier.fillMaxWidth().padding(14.dp, 0.dp, 14.dp, 0.dp),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }

}
