package com.samuel.oremoschanganapt.components

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.samuel.oremoschanganapt.components.buttons.NormalButton
import com.samuel.oremoschanganapt.db.CommonViewModel
import com.samuel.oremoschanganapt.db.LovedDataPojo
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.DarkColor
import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuel.oremoschanganapt.view.sideBar.NormalText
import com.samuelsumbane.oremoschanganapt.db.Pray
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.Song
import com.samuelsumbane.oremoschanganapt.db.SongViewModel


fun toastAlert(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT){
    val toast = Toast.makeText(context, text, duration)
    toast.show()
}

@Composable
fun LoadingScreen() {

    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        val iconDefaultColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray

        CircularProgressIndicator(
            modifier = Modifier.width(54.dp),
            color = colorObject.mainColor,
            trackColor = iconDefaultColor,
        )
        Spacer(Modifier.height(20.dp))
        Text("Carregando...")
    }
}

@Composable
fun HomeTexts(
    text: String,
    fontSize: Int
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = fontSize.sp
    )
}

@Composable
fun CommonRow(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        if (subTitle != ""){
            Text(
                text = subTitle,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun SongRow(
    navController: NavController,
    songViewModel: SongViewModel,
    song: Song,
    reloadIcon: Boolean = true
) {
    val mainColor = colorObject.mainColor
    var lovedState by remember { mutableStateOf(song.loved) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable { navController.navigate("eachCantico/${song.songId}") },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .size(40.dp)
                .height(60.dp)
                .border(1.dp, lerp(mainColor, DarkColor, 0.3f), RoundedCornerShape(50))
                .align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = song.number,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.width(4.dp))

        Row (
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(mainColor, lerp(mainColor, DarkColor, 0.9f)),
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CommonRow(song.title, song.subTitle, Modifier.weight(1f))
            Row(Modifier.padding(end = 10.dp)) {
                StarButton(lovedState) {
                    if (lovedState) {
                        songViewModel.setLovedSong(song.songId, false)
                    } else {
                        songViewModel.setLovedSong(song.songId, true)
                    }
                    if (reloadIcon) lovedState = !lovedState
                }
            }
        }
    }
}

@Composable
fun StarButton(
    lovedState: Boolean,
    onClick: () -> Unit
) {
    // Icon size animation ------->>
    val scale = remember { androidx.compose.animation.core.Animatable(1f) }
    val iconDefaultColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
    val iconColor by animateColorAsState(
        targetValue = if (lovedState) Orange else iconDefaultColor,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(lovedState) {
        if (lovedState) {
            // Execute animation scale when "Loving" ------>>
            scale.animateTo(
                targetValue = 1.5f, // Increase to 1.5x ------>>
                animationSpec = tween(durationMillis = 200)
            )
            scale.animateTo(
                targetValue = 1f, // Back to normal size ------->>
                animationSpec = tween(durationMillis = 200)
            )
        } else {
            // Sure that scale stays on normal size ------>>
            scale.snapTo(1f)
        }
    }

    IconButton(
        modifier = Modifier.size(35.dp),
        onClick = { onClick() },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Icon(
            imageVector = if (lovedState) Icons.Default.Star else Icons.Outlined.Star,
            contentDescription = if (lovedState) "É favorito" else "Não é favorito",
            tint = iconColor,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                )
        )
    }
}


@Composable
fun PrayRow(
    navController: NavController,
    prayViewModel: PrayViewModel,
    pray: Pray,
    reloadIcon: Boolean = true
) {
    val mainColor = colorObject.mainColor
    var lovedState by remember { mutableStateOf(pray.loved) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(55.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(mainColor, lerp(mainColor, DarkColor, 0.9f)),
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp, 0.dp, 0.dp, 0.dp)
            .clickable {
                navController.navigate("eachOracao/${pray.prayId}")
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.9f)
                .fillMaxHeight()
        ) {
            CommonRow(pray.title, pray.subTitle, Modifier.weight(1f))
        }

        Row(Modifier.padding(end = 10.dp)) {
            StarButton(lovedState) {
                if (lovedState) {
                    prayViewModel.setLovedPray(pray.prayId, false)
                } else {
                    prayViewModel.setLovedPray(pray.prayId, true)
                }
                if (reloadIcon) lovedState = !lovedState
            }
        }
    }
}

@Composable
fun DefTabButton(content: @Composable () -> Unit){
    Card(
        Modifier.fillMaxWidth()
            .background(Color.Transparent, RoundedCornerShape(10.dp)),
        elevation = CardDefaults.elevatedCardElevation(3.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) { content() }
    }

}

@Composable
fun TextIconRow(title: String, showContent: Boolean, modifier: Modifier) {
    val mainColor = colorObject.mainColor
    val rS = 9.dp // rowShape ---------->>
    val color = Color.White

    Row (
        modifier = modifier.fillMaxSize().height(45.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(mainColor, lerp(mainColor, DarkColor, 0.9f)),
                ), shape = if (showContent)
                    RoundedCornerShape(rS, rS, 0.dp, 0.dp) else RoundedCornerShape(rS) )
            .padding(10.dp),
        Arrangement.SpaceBetween
    ) {
        Text(title, color = color, fontSize = 17.sp)
        if (showContent)
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Open or Close", tint = Color.White)
        else
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Open or Close", tint = Color.White)

    }
}

@Composable
fun FilePickerScreen(viewModel: CommonViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.openInputStream(it)?.use { inputStream ->
                    val json = inputStream.bufferedReader().use { it.readText() }
                    viewModel.restoreBackup(json, context) // Send data to ViewModel ------->>
                }
            } ?: run {
                Toast.makeText(context, "Nenhum arquivo selecionado.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    NormalButton("Restourar\nDados") { launcher.launch(arrayOf("application/json")) }
}

@Composable
fun BackupPickerScreen(viewModel: CommonViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json"),
        onResult = { uri ->
            uri?.let {
                val lovedPrays = viewModel.getLovedPrays()
                val lovedSongs = viewModel.getLovedSongs()

                val lovedDataList = mutableListOf<LovedDataPojo>().apply {
                    lovedPrays.forEach { add(LovedDataPojo(it.prayId, "Pray")) }
                    lovedSongs.forEach { add(LovedDataPojo(it.songId, "Song")) }
                }

                if (lovedDataList.isEmpty()) {
                    Toast.makeText(context, "Nenhum dado encontrado para exportar.", Toast.LENGTH_SHORT).show()
                    return@let
                }

                val json = Gson().toJson(lovedDataList)

                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.write(json.toByteArray())
                    outputStream.flush()
                    Toast.makeText(context, "Backup salvo com sucesso!", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(context, "Nenhum local selecionado para salvar.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    NormalButton ("Fazer\nBackup", ) {
        launcher.launch("lovedItems_backup.json")
    }
}
