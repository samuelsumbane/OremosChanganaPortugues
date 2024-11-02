package com.samuel.oremoschanganapt.components

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


fun toastAlert(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT){
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(context, text, duration)
    toast.show()
}


@Composable
fun addReminder(
    onClick: () -> Unit
){
    IconButton(
        onClick = onClick
    ) {
        Icon(Icons.Default.Info, contentDescription = "Reminder")
    }
}

//@Composable
//fun reminder(
//    onClick: () -> Unit
//){
//    IconButton(
//        onClick = onClick
//    ) {
//        Icon(Icons.Default.Info, contentDescription = "Reminder")
//    }
//}

@Composable
fun ZoomingLoadingEffect(
    text: String,
    durationMillis: Int = 1000,
    fontSize: TextUnit = 20.sp
) {
    // Controla o fator de escala do texto
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)) {
        Text(
            text = text, fontWeight = FontWeight.Bold, fontSize = fontSize,
            fontFamily = FontFamily.Cursive
        )
    }
}

@Composable
fun LoadingScreen(text: String = "Oremos Changana-PT") {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ZoomingLoadingEffect(
                text = text,
                durationMillis = 1000,
                fontSize = 32.sp
            )
        }
    }
}
