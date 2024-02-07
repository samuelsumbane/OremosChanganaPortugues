package com.samuel.oremoschangana.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import com.samuel.oremoschangana.ui.theme.ShapeEditText

@OptIn(ExperimentalMaterial3Api::class)
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
        label = { Text(text = label) },
        maxLines = maxLines,
        shape = ShapeEditText.medium
    )
}