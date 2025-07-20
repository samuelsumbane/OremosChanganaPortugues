package com.samuel.oremoschanganapt.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuel.oremoschanganapt.components.textFontSize
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.ui.theme.DarkColor
import com.samuel.oremoschanganapt.ui.theme.DarkSecondary
import com.samuel.oremoschanganapt.ui.theme.LightSecondary

@Composable
fun MorePagesBtn(
    icon: ImageVector,
    description: String,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val mainColor = ColorObject.mainColor
    val secondColor = ColorObject.secondColor

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
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
                shape = RoundedCornerShape(16.dp)
            )
            .height(120.dp),
        contentPadding = PaddingValues(15.dp),
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, color = Color.White, fontSize = textFontSize())
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
) {
    val bgColor = if (!isSystemInDarkTheme()) LightSecondary else DarkSecondary

    IconButton(
        modifier = modifier.then(Modifier.size(45.dp)),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = bgColor,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ) {
        Icon(icon, contentDescription = description, modifier = iconModifier, tint = Color.White)
    }
}


@Composable
fun NormalButton(
    text: String,
    btnColor: Color = ColorObject.mainColor.copy(0.85f),
    onClick: () -> Unit
) {
    val bgColor = btnColor.copy(0.2f)

    Button(
        onClick = onClick,
        modifier = Modifier.width(95.dp).height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = btnColor
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text, color = btnColor)
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
            Icon(icon, contentDescription = "language icon")
            Text(text = title, fontSize = textFontSize())
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Open appearance"
            )
        }
    }
}

@Composable
fun ScrollToFirstItemBtn(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        )
    ) {
        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Scroll para cima", tint = MaterialTheme.colorScheme.background, modifier = Modifier.size(35.dp))
    }
}