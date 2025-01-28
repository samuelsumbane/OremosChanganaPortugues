package com.samuel.oremoschanganapt.components

import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuel.oremoschanganapt.repository.colorObject

@Composable
fun InputSearch(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    inputColor: Color = colorObject.inputColor,
) {
    val textColor = MaterialTheme.colorScheme.tertiary
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.background(inputColor, RoundedCornerShape(15.dp))
            .height(40.dp).padding(start=10.dp, top=5.dp)
            .fillMaxWidth(0.85f)
            .then(modifier),
        textStyle = TextStyle(color = textColor, fontSize = 17.sp),
    )
}

