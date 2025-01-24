package com.samuel.oremoschanganapt.view.sideBar

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun About(){
    val scrollState = rememberScrollState()
    val textColor = MaterialTheme.colorScheme.onPrimary
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Oremos Changana - Português",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = textColor
            )
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Programador",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textColor
            )
            Text(text = "Samuel Eugénio Sumbane", fontSize = 16.sp,
                color = textColor)
            Text(text = "Programador Full-Stack", fontSize = 16.sp,
                color = textColor)
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Contacto",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp ,
                color = textColor
            )

            Text(text = "+258 865230661 / +258 833597867", fontSize = 16.sp, lineHeight = 24.sp,
                color = textColor)
            Text(text = "samuelsumbane143@gmail.com", fontSize = 16.sp, lineHeight = 24.sp,
                color = textColor)
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Apoio",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp, lineHeight = 24.sp,
                color = textColor
            )

            Text(
                text = "A produção deste aplicativo carreceu de alguns custos da parte do programador, neste sentido pretende-se produzir mais aplicativos desta natureza e para tal contamos com seu apoio financeiro que pode ser efectuado através do numero 865230661.",
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(17.dp, 0.dp, 7.dp, 0.dp),
                fontSize = 16.sp, lineHeight = 24.sp,
                color = textColor
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Versão: 3.0", color = textColor, fontSize = 16.sp, lineHeight = 24.sp,
                modifier = Modifier.padding(bottom = 20.dp))
        }
    }
}

