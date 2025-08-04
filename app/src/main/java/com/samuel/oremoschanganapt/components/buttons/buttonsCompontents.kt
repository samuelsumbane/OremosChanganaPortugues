package com.samuel.oremoschanganapt.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.textFontSize
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.ui.theme.DarkSecondary
import com.samuel.oremoschanganapt.ui.theme.LightSecondary
import com.samuel.oremoschanganapt.ui.theme.RedButton

@Composable
fun MorePagesBtn(
    icon: ImageVector,
    description: String,
    text: String,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .background(color = ColorObject.mainColor, shape = shape)
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
            Text(text = text, color = Color.White, fontSize = textFontSize(), fontWeight = FontWeight.SemiBold)
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
    btnColor: Color = ColorObject.mainColor,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(95.dp).height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text, color = Color.White)
    }
}

@Composable
fun CancelButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = ColorObject.mainColor, containerColor = Color.Transparent
        ),
    ) {
        Text(text = text)
    }
}

@Composable
fun submitButtonsRow(
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        content()
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
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorObject.mainColor,
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = "icon", tint = Color.White)
            Text(text = title, fontSize = textFontSize(), color = Color.White)
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Open ou close tab",
                tint = Color.White
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