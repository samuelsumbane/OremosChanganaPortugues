package com.samuel.oremoschangana.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.samuel.oremoschangana.R
import com.samuel.oremoschangana.ui.theme.splashColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SplashWindow(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    // Simular um atraso para a splash screen
    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            delay(3000)
            navController.navigate("home")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(splashColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.oremospic),
            contentDescription = null,
            modifier = Modifier
//                .fillMaxSize()
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )

        Text(text="Oremos ", color = MaterialTheme.colorScheme.onPrimary, fontSize = 27.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        Text(text="Changana - Português", color = MaterialTheme.colorScheme.onPrimary, fontSize = 25.sp, fontStyle = FontStyle.Italic)
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashWindow(navController = rememberNavController())
}
