package com.samuel.oremoschanganapt.components.buttons

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuel.oremoschanganapt.ui.theme.Typography
import com.samuel.oremoschanganapt.ui.theme.White
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.SongViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun MorePagesBtn(
    text: String,
    shape: RoundedCornerShape,
    onClick: () -> Unit
){
    val col = colorObject.mainColor

    Button(
        modifier = Modifier.width(140.dp).height(150.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        colors = ButtonDefaults.buttonColors(containerColor = col),
        shape = shape,
        contentPadding = PaddingValues(15.dp),
        onClick = onClick
    ) {
        Text(text = text, style = Typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun ShortcutButtonChild(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    description: String,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit
){
    val textColor = MaterialTheme.colorScheme.onPrimary
    IconButton(
        modifier = modifier.then(Modifier.size(45.dp)),
        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = textColor),
        onClick = onClick
    ) {
        Icon(icon, contentDescription = description, modifier = iconModifier, tint = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
fun StarButton(
    itemLoved: Boolean,
    prayViewModel: PrayViewModel?,
    songViewModel: SongViewModel?,
    id: Int,
    view: String
): Boolean{
    var loved by remember { mutableStateOf(itemLoved) }
    IconButton(
        modifier = Modifier.size(50.dp),
        onClick = {
            loved = !itemLoved
            if (view == "prayViewModel"){
                prayViewModel?.updatePray(id, loved)
            } else {
                songViewModel?.updateSong(id, loved)
            }
        }
    ){
        if (loved){
            Icon(imageVector = Icons.Default.Star, contentDescription = "É favorito", tint = Orange)
        } else {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = "Não é favorito", tint = White)
        }
    }

    return loved
}

