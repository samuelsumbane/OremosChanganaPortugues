package com.samuel.oremoschanganapt.components
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.functionsKotlin.colorToString
import com.samuel.oremoschanganapt.ui.theme.*


@Preview
@Composable
fun colorPickerDemo(lastColor: Color = Lightblue, withIcon: Boolean = true): String {
    var expanded by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(lastColor) }

    val colors = listOf(
        Lightgray, Lightblue, Blue, Turquoise, Green,  Pink, Tomato, Red, Purple
    )

    Column(
        modifier = Modifier
            .width(50.dp),
//            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//
        if (withIcon) {
            IconButton(onClick = { expanded = true },
                modifier = Modifier
                    .size(50.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(60.dp))
            ) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.palette_24)
                    , contentDescription = "Color palette", tint = lastColor
                )
            }

        } else {
            Button(onClick = { expanded = true },
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors( containerColor = lastColor )
            ){}
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = true)
        ) {
            colors.forEach { color ->
                DropdownMenuItem(
                    modifier = Modifier.width(60.dp),
                    text = {
                        Box(
                            modifier = Modifier
                                .size(45.dp, 24.dp)
                                .background(color)
                        )
                    },
                    onClick = {
                        selectedColor = color
                        expanded = false
                    }
                )
            }
        }
    }
    return colorToString(selectedColor)
}

