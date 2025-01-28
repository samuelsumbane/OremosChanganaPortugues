package com.samuel.oremoschanganapt.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextForegroundStyle.Unspecified.brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.DarkColor
//import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuel.oremoschanganapt.ui.theme.Typography
import com.samuel.oremoschanganapt.ui.theme.White
import com.samuelsumbane.oremoschanganapt.db.Pray
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.Song
import com.samuelsumbane.oremoschanganapt.db.SongViewModel


//@Composable
//fun MorePagesBtn(
//    text: String,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit
//){
//    val mainColor = colorObject.mainColor
//
//    Button(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(120.dp),
////        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
//        colors = ButtonDefaults.buttonColors(containerColor = mainColor),
//        shape = RoundedCornerShape(14.dp),
//        contentPadding = PaddingValues(15.dp),
//        onClick = onClick
//    ) {
//        Text(text = text, style = Typography.titleMedium, color = MaterialTheme.colorScheme.tertiary)
//    }
//}

//@Composable
//fun MorePagesBtn(icon: ImageVector, text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
//    val mainColor = colorObject.mainColor
//
//    Button(
//        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
//        modifier = modifier
//            .fillMaxWidth()
//            .background(
//                brush = Brush.horizontalGradient(
//                    colors = listOf(mainColor, lerp(mainColor, MaterialTheme.colorScheme.background, 0.9f)),
//                ),
//                shape = RoundedCornerShape(16.dp)
//            )
//            .height(120.dp),
//        contentPadding = PaddingValues(15.dp),
//
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Icon(
//                imageVector = icon,
//                contentDescription = null,
//                modifier = Modifier.size(24.dp),
//                tint = Color.White
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = text, color = Color.White, fontSize = 16.sp)
//        }
//    }
//}

@Composable
fun MorePagesBtn(icon: String, text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val mainColor = colorObject.mainColor
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(mainColor, lerp(mainColor, DarkColor, 0.9f)),
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .height(120.dp),
        contentPadding = PaddingValues(15.dp),
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(icon, fontSize = 25.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, color = Color.White, fontSize = 16.sp)
        }
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
    IconButton(
        modifier = modifier.then(Modifier.size(45.dp)),
        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Icon(icon, contentDescription = description, modifier = iconModifier, tint = MaterialTheme.colorScheme.secondary)
    }
}


@Composable
fun NormalButton(text: String,
                 btnColor: Color,
                 textColor: Color = Color.White,
                 onClick: () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text)
    }
}

@Composable
fun ExpandContentTabBtn(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                icon,
//                imageVector = ImageVector.vectorResource(R.drawable.grid_view_24),
                contentDescription = "language icon"
            )
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Open appearance"
            )
        }
    }

}