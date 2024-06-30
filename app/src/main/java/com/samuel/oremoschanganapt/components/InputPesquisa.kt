package com.samuel.oremoschanganapt.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.samuel.oremoschanganapt.ui.theme.ShapeEditText

@Composable
fun InputPesquisa(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    label: String,
    maxLines: Int,
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier,
        label = { Text(text = label, color = Color.White) },
        maxLines = maxLines,
        shape = ShapeEditText.medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.White
        ),
        textStyle = TextStyle(color = Color.White)
    )
}