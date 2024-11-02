package com.samuel.oremoschanganapt.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.samuel.oremoschanganapt.repository.colorObject
import com.samuel.oremoschanganapt.ui.theme.ShapeEditText

@Composable
fun InputPesquisa(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    label: String,
    inputColor: Color = Color.White,
    maxLines: Int,
){
//    val inputColor = colorObject.inputColor

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier,
        label = { Text(text = label, color = MaterialTheme.colorScheme.onPrimary) },
        maxLines = maxLines,
        shape = ShapeEditText.medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputColor,
            unfocusedContainerColor = inputColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
    )
}