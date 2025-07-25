package com.samuel.oremoschanganapt.components

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.db.data.Song
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.repository.FontSize
import com.samuel.oremoschanganapt.ui.theme.DarkColor
import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuel.oremoschanganapt.view.states.UIState.configFontSize
import com.samuel.oremoschanganapt.db.data.Pray
import kotlinx.coroutines.Dispatchers
//import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
//import com.samuelsumbane.oremoschanganapt.db.SongViewModel
import kotlinx.coroutines.withContext


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
            color = ColorObject.mainColor,
            trackColor = iconDefaultColor,
        )
        Spacer(Modifier.height(20.dp))
        Text("${stringResource(R.string.loading)}...")
    }
}

@Composable
fun HomeTexts(text: String, fontSize: Int) {
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
            fontSize = textFontSize(),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )

        if (subTitle.isNotBlank()){
            val fontSizeUnit = textFontSize().value - 5
            Text(
                text = subTitle,
                fontSize = fontSizeUnit.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RadioButtonDialog(
    showDialog: Boolean,
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = {
                Column {
                    options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (option == selectedOption),
                                    onClick = { onOptionSelected(option) },
                                    role = Role.RadioButton
                                )
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = (option == selectedOption),
                                onClick = { onOptionSelected(option) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(18.dp),
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SongRow(
    navController: NavController,
    song: Song,
    blackBackground: Boolean = false,
    loved: Boolean = false,
    showStarButton: Boolean = true,
    onToggleLoved: (Int) -> Unit = {},
) {
    val mainColor = ColorObject.mainColor
    var lovedIdSongs by remember { mutableStateOf( mutableSetOf<Int>()) }
//    var lovedState by remember { mutableStateOf(song.id in lovedIdSongs) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable { navController.navigate("eachCantico/${song.id}") },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .size(40.dp)
                .height(60.dp)
                .border(1.dp, lerp(mainColor, ColorObject.secondColor, 0.3f), RoundedCornerShape(50))
                .align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val songNumberColor = if (blackBackground) Color.White else MaterialTheme.colorScheme.tertiary
            Text(
                text = song.number,
                fontSize = (textFontSize().value - 5).sp,
                color = songNumberColor,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.width(6.dp))

        Row (
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            mainColor,
                            lerp(
                                start = mainColor,
                                stop = if (ColorObject.secondColor == Color.Unspecified) ColorObject.mainColor else ColorObject.secondColor,
                                fraction = 0.9f
                            )
                        ),
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CommonRow(song.title, song.subTitle, Modifier.weight(1f))
            Row(Modifier.padding(end = 10.dp)) {
                if (showStarButton) {
                    StarButton(loved) {
                        onToggleLoved(song.id)
                    }
                }
            }
        }
    }
}


@Composable
fun lazyColumn(content:  LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) { content() }
}

@Composable
fun PrayRow(
    navController: NavController,
    pray: Pray,
    loved: Boolean = false,
    showStarButton: Boolean = true,
    onToggleLoved: (Int) -> Unit = {},
) {
    val mainColor = ColorObject.mainColor
    val secondColor = ColorObject.secondColor

    with(pray) {
        Row(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxSize()
                .height(55.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            mainColor,
                            lerp(
                                start = mainColor,
                                stop = if (secondColor == Color.Unspecified) mainColor else secondColor,
                                fraction = 0.9f
                            )
                        ),
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { navController.navigate("eachOracao/$id") },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.9f)
                    .fillMaxHeight()
            ) {
                CommonRow(title, subTitle, Modifier.weight(1f))
            }

            Row(Modifier.padding(end = 10.dp)) {
                if (showStarButton) {
                    StarButton(loved) {
                        onToggleLoved(pray.id)
                    }
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
fun ColorPickerHSV(
    modifier: Modifier = Modifier,
    size: Int = 256,
    initialColor: Color = Color.Green,
    isSolidColorTabSelected: (Boolean) -> Unit,
    onColorChanged: (Color) -> Unit,
    onSecondColorChanged: (Color) -> Unit
) {
    var hue by remember { mutableStateOf(0f) }
    var hue2 by remember { mutableStateOf(0f) }
    var saturation by remember { mutableStateOf(1f) }
    var saturation2 by remember { mutableStateOf(1f) }
    val value = 1f // luminosity fixed value

    var selectorPosition by remember { mutableStateOf(Offset.Zero) }
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val selectedColor = Color.hsv(hue, saturation, value)
    val secondSelectedColor = Color.hsv(hue2, saturation2, value)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    var setSecondColor by remember {
        mutableStateOf(ColorObject.secondColor != Color.Unspecified)
    }

    val columnW by remember(screenWidth) {
        derivedStateOf { screenWidth - (screenWidth * 0.35) }
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.solid),
        stringResource(R.string.gradient),
    )
    val typography = MaterialTheme.typography
    var firstColorBoxSelected by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        val hsvArray = FloatArray(3)
        android.graphics.Color.colorToHSV(initialColor.toArgb(), hsvArray)
        hue = hsvArray[0]
        saturation = hsvArray[1]

        val x = (hue / 360f) * (size - 1)
        val y = saturation * (size - 1)
        selectorPosition = Offset(x, y)

        isSolidColorTabSelected(true)
        onColorChanged(Color.hsv(hue, saturation, value))
        onSecondColorChanged(Color.hsv(hue2, saturation2, value))
    }

    LaunchedEffect(value) {
        withContext(Dispatchers.Default) {
            val bmp = ImageBitmap(size, size)
            val canvas = androidx.compose.ui.graphics.Canvas(bmp)
            val paint = Paint()
            for (x in 0 until size) {
                for (y in 0 until size) {
                    val h = (x / (size - 1f)) * 360f
                    val s = y / (size - 1f)
                    paint.color = Color.hsv(h, s, value)
                    canvas.drawRect(Rect(x.toFloat(), y.toFloat(), x + 1f, y + 1f), paint)
                }
            }
            bitmap = bmp
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Spacer(Modifier.height(60.dp))
        Box(
            modifier = Modifier.size(200.dp).pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val x = change.position.x.coerceIn(0f, size - 1f)
                        val y = change.position.y.coerceIn(0f, size - 1f)

                        selectorPosition = Offset(x, y)
                        if (firstColorBoxSelected) {
                            hue = (x / (size - 1f)) * 360f
                            saturation = y / (size - 1f)
                            onColorChanged(Color.hsv(hue, saturation, value))
                        } else {
                            hue2 = (x / (size - 1f)) * 360f
                            saturation2 = y / (size - 1f)
                            onSecondColorChanged(Color.hsv(hue2, saturation2, value))
                        }
                    }
                }) {
            if (bitmap != null) {
                Image(bitmap = bitmap!!, contentDescription = null)

                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.White,
                        radius = 10.dp.toPx(),
                        center = selectorPosition,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        @Composable
        fun FirstColorPreviewer() {
            Spacer(Modifier.height(18.dp))
            spaceAroundContentWidget {
                Text(text = stringResource(R.string.principal_color))
                colorSelectBox(
                    color = selectedColor,
                    selected = firstColorBoxSelected,
                ) { firstColorBoxSelected = true }
            }
        }

        Spacer(Modifier.height(20.dp))

        TabRow(
            selectedTabIndex = selectedTabIndex, modifier = Modifier.padding(10.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = {
                        Text(
                            text = tab,
                            style = typography.bodyMedium,
                            fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index

                        firstColorBoxSelected =
                            if (index == 0) {
                                onSecondColorChanged(Color.Unspecified)
                                isSolidColorTabSelected(true)
                                true
                            } else {
                                isSolidColorTabSelected(false)
                                false
                            }
                    },
                    selectedContentColor = ColorObject.mainColor,
                )
            }
        }

        AnimatedContent(
            targetState = selectedTabIndex,
            transitionSpec = {
                slideIntoContainer(
                    animationSpec = tween(400, easing = EaseIn), towards = Up
                ).togetherWith(
                    slideOutOfContainer(
                        animationSpec = tween(450, easing = EaseOut), towards = Down
                    )
                )
            },
        ) { selectedTabIndex ->
            Column(
                modifier = Modifier.fillMaxSize(0.8f),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (selectedTabIndex) {
                    0 -> FirstColorPreviewer()
                    1 -> {
                        FirstColorPreviewer()

                        Spacer(Modifier.height(16.dp))

                        spaceAroundContentWidget {
                            Text(text = stringResource(R.string.secondary_color))
                            colorSelectBox(
                                color = secondSelectedColor,
                                selected = !firstColorBoxSelected,
                            ) { firstColorBoxSelected = false }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun colorSelectBox(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(60.dp)
            .background(color, shape = CircleShape)
            .border(
                width = if (selected) 4.dp else 1.dp,
                color = if (selected) Color.Gray else Color.Black,
                shape = CircleShape)
            .clickable { onClick() }
    )
}

@Composable
fun spaceAroundContentWidget(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(0.8f),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) { content() }
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
    val mainColor = ColorObject.mainColor
    val rS = 9.dp // rowShape ---------->>

    Row (
        modifier = modifier
            .fillMaxSize()
            .height(45.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(mainColor, lerp(mainColor, ColorObject.secondColor, 0.9f)),
                ), shape = if (showContent)
                    RoundedCornerShape(rS, rS, 0.dp, 0.dp) else RoundedCornerShape(rS)
            ),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = MaterialTheme.colorScheme.tertiary, fontSize = 17.sp)
            if (showContent)
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Close", tint = Color.White)
            else
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Open", tint = Color.White)
        }
    }
}

@Composable
fun KeyValueTextRow(
    key: String,
    value: String,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .height(30.dp)
            .clickable { onClick() },
        Arrangement.SpaceBetween
    ) {
        Text(key, fontSize = textFontSize())
        Text(value, fontSize = textFontSize(), fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun textFontSize() = FontSize.fromString(configFontSize).size

@Composable
fun pagerContent(
    navController: NavController,
    modifier: Modifier,
    title: String,
    subTitle: String,
    body: String,
    showShortcutButton: Boolean = true
) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = (textFontSize().value + 2).sp,
                textAlign = TextAlign.Center,
                softWrap = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = subTitle,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = body.trimIndent(),
                fontSize = textFontSize(),
                softWrap = true,
                modifier = Modifier.padding(15.dp).fillMaxWidth()
            )
        }

        if (showShortcutButton) ShortcutsButton(navController)
    }
}

//@Composable
//fun FilePickerScreen(viewModel: CommonViewModel) {
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenDocument(),
//        onResult = { uri ->
//            uri?.let {
//                context.contentResolver.openInputStream(it)?.use { inputStream ->
//                    val json = inputStream.bufferedReader().use { it.readText() }
//                    viewModel.restoreBackup(json, context) // Send data to ViewModel ------->>
//                }
//            } ?: run {
//                Toast.makeText(context, "Nenhum arquivo selecionado.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    )
//
//    NormalButton("Restourar\nDados") { launcher.launch(arrayOf("application/json")) }
//}

//@Composable
//fun BackupPickerScreen(viewModel: CommonViewModel) {
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.CreateDocument("application/json"),
//        onResult = { uri ->
//            uri?.let {
//                val lovedPrays = viewModel.getLovedPrays()
//                val lovedSongs = viewModel.getLovedSongs()
//
//                val lovedDataList = mutableListOf<LovedDataPojo>().apply {
//                    lovedPrays.forEach { add(LovedDataPojo(it.prayId, "Pray")) }
//                    lovedSongs.forEach { add(LovedDataPojo(it.songId, "Song")) }
//                }
//
//                if (lovedDataList.isEmpty()) {
//                    Toast.makeText(context, "Nenhum dado encontrado para exportar.", Toast.LENGTH_SHORT).show()
//                    return@let
//                }
//
//                val json = Gson().toJson(lovedDataList)
//
//                context.contentResolver.openOutputStream(it)?.use { outputStream ->
//                    outputStream.write(json.toByteArray())
//                    outputStream.flush()
//                    Toast.makeText(context, "Backup salvo com sucesso!", Toast.LENGTH_SHORT).show()
//                }
//            } ?: run {
//                Toast.makeText(context, "Nenhum local selecionado para salvar.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    )
//
//    NormalButton ("Fazer\nBackup", ) {
//        launcher.launch("lovedItems_backup.json")
//    }
//}


