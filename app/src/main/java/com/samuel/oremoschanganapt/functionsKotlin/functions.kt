package com.samuel.oremoschanganapt.functionsKotlin
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.app.AlarmManager
import android.app.PendingIntent
import androidx.compose.ui.graphics.Color
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Canvas as GraphicsCanvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import java.time.ZoneId
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.samuel.oremoschanganapt.ReminderReceiver
import com.samuel.oremoschanganapt.components.colorSelectBox
import com.samuel.oremoschanganapt.components.spaceAroundContentWidget
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.ui.theme.Blue
import com.samuel.oremoschanganapt.ui.theme.Green
import com.samuel.oremoschanganapt.ui.theme.Lightblue
import com.samuel.oremoschanganapt.ui.theme.Lightgray
import com.samuel.oremoschanganapt.ui.theme.Pink
import com.samuel.oremoschanganapt.ui.theme.Purple
import com.samuel.oremoschanganapt.ui.theme.Red
import com.samuel.oremoschanganapt.ui.theme.Tomato
import com.samuel.oremoschanganapt.ui.theme.Turquoise
import com.samuel.oremoschanganapt.view.morepagesPackage.ChanganaTabContent
import com.samuel.oremoschanganapt.view.morepagesPackage.PtTabContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset


@SuppressLint("ScheduleExactAlarm")
@RequiresApi(Build.VERSION_CODES.M)
fun scheduleNotificationForSongOrPray(context: Context, title: String, message: String, timestamp: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("NOTIFICATION_TITLE", title)
        putExtra("NOTIFICATION_MESSAGE", message)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        timestamp,
        pendingIntent
    )
}


fun getCurrentTimestamp(): Long = System.currentTimeMillis()

// Verify if is number or not --------->>
fun isNumber(valor: Any): Boolean {
    return try {
        // Tenta converter o valor para um tipo numérico
        when (valor) {
            is Byte, is Short, is Int, is Long, is Float, is Double -> true
            is String -> {
                // Try to convert string to number ---------->>
                valor.toDouble()
                true
            }
            else -> false
        }
    } catch (e: NumberFormatException) {
        // Captura a exceção se a conversão falhar
        false
    }
}


fun shareText(context: Context, text: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    context.startActivity(Intent.createChooser(shareIntent, "Compartilhar via"))
}

@Composable
fun ShareIconButton(context: Context, text: String) {
    IconButton(
        onClick = { shareText(context, text) },
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = null
        )
    }
}


// This function returns the string of  Color -------->>
fun colorToString(color: Color): String{
    return when(color){
        Red -> "Red"
        Pink -> "Pink"
        Tomato -> "Tomato"
        Lightblue -> "Lightblue"
        Lightgray -> "Lightgray"
        Blue -> "Blue"
        Purple -> "Purple"
        Green -> "Green"
        Turquoise -> "Turquoise"
        else -> "404"
    }
}

// This function returns the color from string --------->>
fun stringToColor(value: String): Color{
    return when(value){
        "Red" -> Red
        "Pink" -> Pink
        "Tomato" -> Tomato
        "Lightblue" -> Lightblue
        "Lightgray" -> Lightgray
        "Blue" -> Blue
        "Purple" -> Purple
        "Green" -> Green
        "Turquoise" -> Turquoise
        else -> Color.DarkGray
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun convertLongToDateString(long: Long): String{
    val instant = Instant.ofEpochMilli(long)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
fun convertTimePickerStateToLong(timePickerState: TimePickerState): Long {
    val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
    return selectedTime.toSecondOfDay() * 1000L
}


@RequiresApi(Build.VERSION_CODES.O)
fun convertLongToTimeString(timeInMillis: Long): String {
    val time = Instant.ofEpochMilli(timeInMillis)
        .atOffset(ZoneOffset.UTC)
        .toLocalTime()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return time.format(formatter)
}


@RequiresApi(Build.VERSION_CODES.O)
fun dateStringToLong(dateInString: String): Long{
    val now = LocalDateTime.now()
    return when (dateInString) {
        "Today" -> now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        "Tomorrow" -> now.plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        "Next week" -> now.plusWeeks(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        "Next Month" -> now.plusMonths(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        else -> throw IllegalArgumentException("Invalid date string")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun timeStringToLong(timeOfDay: String): Long {
    val localTime = when (timeOfDay) {
        "Morning (9:00)" -> LocalTime.of(9, 0)
        "Noon (12:00)" -> LocalTime.of(12, 0)
        "Afternoon (15:00)" -> LocalTime.of(15, 0)
        "Evening (18:00)" -> LocalTime.of(18, 0)
        "Late evening (21:00)" -> LocalTime.of(21, 0)
        else -> throw IllegalArgumentException("Invalid time of day")
    }
    // Convert LocalTime to Long representing miliseconds
    return localTime.atDate(LocalDate.now())
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun combineTimestamps(dateMillis: Long, timeMillis: Long): Long {
    val data = Instant.ofEpochMilli(dateMillis).atZone(ZoneId.of("Africa/Maputo")).toLocalDate()
    val hora = LocalTime.ofSecondOfDay(timeMillis / 1000)
    val dataHora = LocalDateTime.of(data, hora)
    return dataHora.atZone(ZoneId.of("Africa/Maputo")).toInstant().toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun splitTimestamp(timestamp: Long): Pair<Long, Long> {
    val zoneId = ZoneId.of("Africa/Maputo")
    val dateTime = Instant.ofEpochMilli(timestamp).atZone(zoneId).toLocalDateTime()

    // Extrair a data em milissegundos
    val dateMillis = dateTime.toLocalDate()
        .atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()

    // Extrair o tempo em milissegundos
    val timeMillis = dateTime.toLocalTime().toSecondOfDay() * 1000L
    return Pair(dateMillis, timeMillis)
}

//fun compareWithCurrentTime(targetMillis: Long): String {
//    val currentMillis = System.currentTimeMillis() // Obtém a hora atual em milissegundos
//
//    return when {
//        targetMillis < currentMillis -> "Passado" // A hora está no passado
//        targetMillis - currentMillis <= 1000 -> "Presente" // Está próximo da hora atual (até 1 segundo de diferença)
//        else -> "Futuro" // A hora está no futuro
//    }
//}

fun compareWithCurrentTime(targetMillis: Long): Boolean = System.currentTimeMillis() < targetMillis

