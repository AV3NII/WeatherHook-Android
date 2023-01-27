package com.example.weatherhook.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.weatherhook.R

@Composable
fun HookName() {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 5.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(R.drawable.baseline_drive_file_rename_outline_24), contentDescription = "Name Icon")
            TextField(value = textState.value, onValueChange = { textState.value = it })
        }
    }
}

@Composable
fun HookInformation() {
    HookName()
}