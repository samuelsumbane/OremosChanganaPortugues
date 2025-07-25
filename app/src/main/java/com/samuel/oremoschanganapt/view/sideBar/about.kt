package com.samuel.oremoschanganapt.view.sideBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.textFontSize
import com.samuel.oremoschanganapt.ui.theme.Typography



@Composable
fun NormalText(text: String, modifier: Modifier = Modifier) {
    val textColor = MaterialTheme.colorScheme.tertiary

    Text(
        text = text,
        fontSize = textFontSize(),
        color = textColor,
        textAlign = TextAlign.Justify,
        modifier = modifier.padding(17.dp, 0.dp, 17.dp, 0.dp),
    )
}

@Composable
fun SubTitleText(text: String, modifier: Modifier = Modifier) {
    val textColor = MaterialTheme.colorScheme.tertiary

    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = textColor,
        modifier = Modifier.padding(bottom = 10.dp)
            .then(modifier)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text="Sobre Oremos", color = MaterialTheme.colorScheme.tertiary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={ navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
            )
        },

    ) { paddingVales ->
        val scrollState = rememberScrollState()
        val textColor = MaterialTheme.colorScheme.tertiary
        Column (
            modifier = Modifier.fillMaxSize().padding(paddingVales),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Oremos Changana - Português", style = Typography.titleLarge, color = textColor, modifier = Modifier.padding(top=36.dp), fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(30.dp))

                SubTitleText("Programador")
                NormalText("Samuel Eugénio Sumbane")
                NormalText("Programador Full-Stack")

                Spacer(modifier = Modifier.height(30.dp))

                SubTitleText("Contacto")
                NormalText("+258 865230661 / +258 833597867")
                NormalText("samuelsumbane143@gmail.com")

                Spacer(modifier = Modifier.height(30.dp))

                SubTitleText("Apoio")
                Text(
                    text = "A produção deste aplicativo carreceu de alguns custos da parte do programador, neste sentido pretende-se produzir mais aplicativos desta natureza e para tal contamos com seu apoio financeiro que pode ser efectuado através do numero 865230661.",
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(17.dp, 0.dp, 17.dp, 0.dp),
                    fontSize = textFontSize(), lineHeight = 24.sp,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(35.dp))

                SubTitleText("Agradecimentos")

                Column() {
                    NormalText("Profundos agradecimentos para:")
                    NormalText("- Arcelia Sitoe")
                    NormalText("- Berilio Mate")
                    NormalText("- Mário Langa")
                    NormalText("- Yunura")
                    NormalText("- Zulmira Congolo")
                }

                NormalText("Edição do Oremos físico: 5", modifier = Modifier.padding(bottom = 20.dp, top = 45.dp))

                Spacer(modifier = Modifier.height(30.dp))
                NormalText("Versão do aplicativo: 3.0", modifier = Modifier.padding(bottom = 20.dp))

                Spacer(modifier = Modifier.height(30.dp))
                SubTitleText("Contribuição")

                NormalText("O app Oremos Changana PT é um projecto de código aberto disponível no GitHub e é feito 100% em Kotlin." +
                        "\n Pode contribuir atravez do link:\n https://github.com/samuelsumbane/OremosChanganaPortugues.git",
                    modifier = Modifier.padding(bottom = 20.dp)
                )


            }
        }
    }
}
