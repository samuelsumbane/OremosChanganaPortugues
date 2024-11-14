package com.samuel.oremoschanganapt.components

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.buttons.StarButton
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuelsumbane.oremoschanganapt.db.CommonViewModel
import com.samuelsumbane.oremoschanganapt.db.Pray
import com.samuelsumbane.oremoschanganapt.db.Song


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

@Composable
fun SongRow(
    commonViewModel: CommonViewModel,
    navController: NavController,
    song: Song,
    songId: Int
){
    val mainColor = colorObject.mainColor
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(55.dp)
            .clickable { navController.navigate("eachCantico/${songId}") },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.size(40.dp)
                .height(60.dp).background(mainColor, RoundedCornerShape(50))
                .align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = song.number,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.width(4.dp))

        Row (
            modifier = Modifier.fillMaxHeight().weight(1f)
                .background(mainColor, RoundedCornerShape(25))
        ) {
            Column(
                modifier = Modifier.fillMaxHeight().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                        text = song.title,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )

                if (song.subTitle != ""){
                    Text(
                        text = song.subTitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            val prayLoved = commonViewModel.getLovedItem("Song", song.songId)
            val itemIsLoved: Boolean = prayLoved != null

            StarButton(
                itemLoved = itemIsLoved,
                commonViewModel = commonViewModel,
                id = song.songId,
                itemTable = "Song"
            )
        }
        Spacer(Modifier.width(4.dp))
    }
}


@Composable
fun  PrayRow (
    commonViewModel: CommonViewModel,
    navController: NavController,
    pray: Pray,
){
    val mainColor = colorObject.mainColor

    Row(
        modifier = Modifier.fillMaxSize()
            .height(55.dp).background(mainColor, RoundedCornerShape(14.dp))
            .padding(8.dp, 0.dp, 0.dp, 0.dp)
            .clickable{
                navController.navigate("eachOracao/${pray.prayId}")
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize().weight(0.9f)
                .fillMaxHeight()
        ){
            Column(
                modifier = Modifier.fillMaxHeight().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = pray.title,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )

                if (pray.subTitle != "") {
                    Text(
                        text = pray.subTitle,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }

        val prayLoved = commonViewModel.getLovedItem("Pray", pray.prayId)
        val itemIsLoved: Boolean = prayLoved != null

        StarButton(
            itemLoved = itemIsLoved,
            commonViewModel = commonViewModel,
            id = pray.prayId,
            itemTable = "Pray"
        )
    }
}