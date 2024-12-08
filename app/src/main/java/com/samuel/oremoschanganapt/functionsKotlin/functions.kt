package com.samuel.oremoschanganapt.functionsKotlin
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
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
import android.app.Activity
import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.ZoneId
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.samuel.oremoschanganapt.ui.theme.Blue
import com.samuel.oremoschanganapt.ui.theme.Green
import com.samuel.oremoschanganapt.ui.theme.Lightblue
import com.samuel.oremoschanganapt.ui.theme.Lightgray
import com.samuel.oremoschanganapt.ui.theme.Orangered
import com.samuel.oremoschanganapt.ui.theme.Pink
import com.samuel.oremoschanganapt.ui.theme.Purple
import com.samuel.oremoschanganapt.ui.theme.Red
import com.samuel.oremoschanganapt.ui.theme.Tomato
import java.time.Instant
import java.time.LocalDate
import java.util.Locale
import java.time.ZoneOffset
import java.util.Calendar

//@SuppressLint("ScheduleExactAlarm")
//@OptIn(ExperimentalMaterial3Api::class)
//fun scheduleReminderCheck(context: Context) {
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    var title = ""
//    var text = ""
//    // 1 minuto --------->>
//    val intervalMillis = 60 * 1000L

//    val realm = MyApp.realm
//    realm.writeBlocking {
//        val reminders = this.query<Reminder>(query = "reminderDate == $0", getCurrentDateInMillis()).find()

//        Log.d("Reminder", "${getCurrentDateInMillis()} | ${getCurrentTimeInMillis()}")

//        if (reminders.isNotEmpty()) {
//            for (reminder in reminders) {
//                title = if (reminder.reminderTable == "Pray") {
//                    "A oracao a sua espera"
//                } else {
//                    "O cantico a sua espera"
//                }
//
//                text = reminder.reminderData.toString()
        //
//        title = "kdjfas"
//        text = "dfalsdf"
//
//                val intent = Intent(context, ReminderReceiver::class.java).apply {
//                    putExtra("NOTIFICATION_TITLE", title)
//                    putExtra("NOTIFICATION_MESSAGE", text)
//                }
//
//                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//
//                alarmManager.setRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    System.currentTimeMillis() + intervalMillis,
//                    intervalMillis,
//                    pendingIntent
//                )
//            }
//        }
//    }

//}


// verifica se é um numero ou nao
fun isNumber(valor: Any): Boolean {
    return try {
        // Tenta converter o valor para um tipo numérico
        when (valor) {
            is Byte, is Short, is Int, is Long, is Float, is Double -> true
            is String -> {
                // Tenta fazer a conversão de String para um tipo numérico
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


//@Composable
//fun showNotification(
//    textTitle: String,
//    textContent: String
//){
//    val context = LocalContext.current
//    var builder = NotificationCompat.Builder(context, "my_id")
//        .setSmallIcon(R.drawable.ic_music)
//        .setContentTitle(textTitle)
//        .setContentText(textContent)
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//}


@RequiresApi(Build.VERSION_CODES.O)
fun dateTime(): String{
    val now = LocalDateTime.now()
    val dateFormat = DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy")
    return now.format(dateFormat)
}

@RequiresApi(Build.VERSION_CODES.O)
fun localTime(): String{
    val now = LocalTime.now()
    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    return now.format(timeFormat)
}

@RequiresApi(Build.VERSION_CODES.O)
fun americanFormat(): String{
    val now = LocalDateTime.now()
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return now.format(dateFormat)
}

@RequiresApi(Build.VERSION_CODES.O)
fun nextMonth(): String{

    val now = LocalDate.now().toString()
    val parts = now.split("-")
    var year = parts[0].toInt()
    var month = parts[1].toInt()
    var day = parts[2].toInt() + 30

    if( day > 30){
        month += 1
        day -= 30
    }

    if (month > 12){
        year += 1
        month -= 12
    }

    val rDay = if(day < 10) "0$day" else day
    val rMoth = if(month < 10) "0$month" else month
    return "$year-$rMoth-$rDay"
}

// This function returns the string of  Color

fun colorToString(color: Color): String{
    return when(color){
        Red -> "Red"
        Orangered -> "Orangered"
        Pink -> "Pink"
        Tomato -> "Tomato"
        Lightblue -> "Lightblue"
        Lightgray -> "Lightgray"
        Blue -> "Blue"
        Purple -> "Purple"
        Green -> "Green"
        else -> "404"
    }
}

// This function returns the color from string

fun stringToColor(value: String): Color{
    return when(value){
        "Red" -> Red
        "Orangered" -> Orangered
        "Pink" -> Pink
        "Tomato" -> Tomato
        "Lightblue" -> Lightblue
        "Lightgray" -> Lightgray
        "Blue" -> Blue
        "Purple" -> Purple
        "Green" -> Green
        else -> Color.DarkGray
    }
}


fun textStringToColor(text: String): Color{
    return when(text){
        "White" -> Color.White
        "Black" -> Color.Black
        else -> Color.White
    }
}


fun bitmapToByteArray(bitmap: Bitmap?): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun deleteImage(context: Context, fileName: String) {
    val file = File(context.filesDir, fileName)
    if (file.exists()) {
        file.delete()
    }
}


fun updateLocale(context: Context, locale: Locale) {
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

@RequiresApi(Build.VERSION_CODES.O)
fun longToRealDate(long: Long): String{
    val instant = Instant.ofEpochMilli(long)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//fun convertTimePickerStateToLong(timePickerState: TimePickerState): Long {
//    val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
//    return selectedTime.atDate(LocalDate.now())
//        .atZone(ZoneId.systemDefault())
//        .toInstant()
//        .toEpochMilli()
//}

//@OptIn(ExperimentalMaterial3Api::class)
//fun convertTimePickerStateToLong(timePickerState: TimePickerState): Long {
//    val hoursInMillis = timePickerState.hour * 60 * 60 * 1000L
//    val minutesInMillis = timePickerState.minute * 60 * 1000L
//    return hoursInMillis + minutesInMillis
//}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
fun convertTimePickerStateToLong(timePickerState: TimePickerState): Long {
    val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
    // Usa UTC para garantir que não haja deslocamento de fuso horário
    return selectedTime.toSecondOfDay() * 1000L
}



//@RequiresApi(Build.VERSION_CODES.O)
//fun convertLongToTimeString(timeInMillis: Long): String {
//    val time = Instant.ofEpochMilli(timeInMillis)
//        .atZone(ZoneId.systemDefault())
//        .toLocalTime()
//
//    val formatter = DateTimeFormatter.ofPattern("HH:mm") // Formato de 24h, pode ajustar
//    return time.format(formatter)
//}

@RequiresApi(Build.VERSION_CODES.O)
fun convertLongToTimeString(timeInMillis: Long): String {
    val time = Instant.ofEpochMilli(timeInMillis)
        .atOffset(ZoneOffset.UTC) // Usa UTC para garantir que não haja deslocamento de fuso horário
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

//fun getCurrentTimeInMillis(): Long {
//    val calendar = Calendar.getInstance()
//    val hours = calendar.get(Calendar.HOUR_OF_DAY)
//    val minutes = calendar.get(Calendar.MINUTE)
//
//    // Ajusta para retornar somente a hora e minuto em milissegundos
//    return hours * 60 * 60 * 1000L + minutes * 60 * 1000L
//}

//@RequiresApi(Build.VERSION_CODES.O)
//fun getCurrentDateTimeInMillis(): Long {
//    return LocalDateTime.now()
//        .atZone(ZoneId.systemDefault())
//        .toInstant()
//        .toEpochMilli()
//}

fun getCurrentTimeInMillis(): Long {
    val calendar = Calendar.getInstance()
    val hoursInMillis = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000L
    val minutesInMillis = calendar.get(Calendar.MINUTE) * 60 * 1000L

    // Retorna somente hora e minuto em milissegundos
    return hoursInMillis + minutesInMillis
}


fun getCurrentDateInMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    // Retorna a data em milissegundos
    return calendar.timeInMillis
}



fun restartActivity(context: Context) {
    val activity = context as? Activity
    activity?.recreate()
}


