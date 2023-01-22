package com.example.weatherhook.ui.components

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherhook.R
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.repository.WeatherHookRepo

val repo: WeatherHookRepo = WeatherHookRepo()
val data = repo.loadAllData().events[1]
var triggerList = data.triggers



// Shows the Event Icons
@Composable
fun Events(weather: Int): Int {

    var weatherSelected by remember { mutableStateOf(weather) }


    var backgroundColor = colorResource(id = R.color.light_green)
    var backgroundColorSelected = colorResource(id = R.color.black_green)
    val icon = arrayOf(painterResource(id = R.drawable.baseline_wb_sunny_24),
        painterResource(id = R.drawable.baseline_cloud_24),
        painterResource(R.drawable.baseline_grain_24),
        painterResource(R.drawable.baseline_snowboarding_24),
        painterResource(R.drawable.baseline_wind_power_24),
        painterResource(R.drawable.baseline_whatshot_24),)
    Card(elevation = 5.dp, shape = RoundedCornerShape(15.dp), modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(40.dp)) {
            for ((index, items) in icon.withIndex()) {
                IconButton(onClick = {weatherSelected = index}, modifier = Modifier.size(24.dp)) {
                    Icon(painter = icon[index], contentDescription = "Icon ${index}", Modifier.scale(1.6f), tint = if (weather == index) {backgroundColorSelected} else backgroundColor)
                }
            }
        }
    }
    return weatherSelected
}

// Slider without steps -> Cloudy
@Composable
fun Cloudy(percentage: Float) {
    var sliderPosition by remember { mutableStateOf(percentage) }
    Card(elevation = 5.dp, shape = RoundedCornerShape(15.dp), modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        Row(modifier = Modifier
            .height(80.dp)
            .padding(30.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(painter = painterResource(R.drawable.baseline_cloud_24), contentDescription = "Cloud", modifier = Modifier.scale(1.6f))
            Slider(value = sliderPosition, valueRange = 0f..1f,  onValueChange = { sliderPosition = it }, modifier = Modifier.width(270.dp))
        }
    }
}

// Slider with steps for multi-use
@Composable
fun SliderSteps(weather: Int,steps: Float, stepsSelected: Float) {

    // 100% -> SliderSteps(n, n-1)

    val icon = arrayOf(painterResource(R.drawable.baseline_grain_24),
        painterResource(R.drawable.baseline_snowboarding_24),
        painterResource(R.drawable.baseline_wind_power_24))
    var sliderPosition by remember { mutableStateOf(stepsSelected) }
    Card(elevation = 5.dp, shape = RoundedCornerShape(15.dp), modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        Row(modifier = Modifier
            .height(80.dp)
            .padding(30.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(painter = icon[weather-2], contentDescription = "Cloud", modifier = Modifier.scale(1.6f))
            Slider(value = sliderPosition, onValueChange = { sliderPosition = it }, steps = (steps.toInt()-2), modifier = Modifier.width(270.dp))
        }
    }
}

// Temp chooser
@Composable
fun Temperature(temperature: Float) {
    var text by remember { mutableStateOf(TextFieldValue(temperature.toInt().toString())) }
    Card(elevation = 5.dp, shape = RoundedCornerShape(15.dp), modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Row(modifier = Modifier
            .height(80.dp)
            .padding(start = 30.dp, end = 30.dp, top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.baseline_whatshot_24), contentDescription = "Cloud", modifier = Modifier.scale(1.6f))
            Spacer(modifier = Modifier.width(150.dp))
            Text(text = "+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Surface(elevation = 3.dp) {
                BasicTextField(
                    value = text,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    onValueChange = {
                        if (it.text.length <= 2) text = it
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(40.dp)
                        .fillMaxSize(),
                    textStyle = TextStyle.Default.copy(fontSize = 27.sp, textAlign = TextAlign.Center),
                    cursorBrush = Brush.verticalGradient(colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))),
                    interactionSource = MutableInteractionSource(),

                    decorationBox = { innerTextField ->
                        Row() {

                            if (text.text.isEmpty()) {
                                Text("14", color = Color.LightGray, fontSize = 27.sp, textAlign = TextAlign.Center)
                            }
                            innerTextField()
                        }
                    }

                )
            }

            Text(text = "°C", fontSize = 20.sp)
            Text(text = "-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun EventChooser(): MutableList<Weather> {
    var _triggerList by remember { mutableStateOf(triggerList) }


    // First Event Choosing
    @Composable
    fun EventChooserElements(weather: Int, intensity: Float) {
        var weatherSelected by remember { mutableStateOf(weather) }
        Column {
            weatherSelected = Events(weatherSelected)
            when (weatherSelected) {
                1 -> {Cloudy(percentage = intensity)
                    weatherSelected = 1}
                2 -> {SliderSteps(2,steps = 4f, stepsSelected = intensity)
                    weatherSelected = 2}
                3 -> {SliderSteps(3,steps = 4f, stepsSelected = intensity)
                    weatherSelected = 3}
                4 -> {SliderSteps(4,steps = 6f, stepsSelected = intensity)
                    weatherSelected = 4}
                5 -> {Temperature(temperature = intensity)
                    weatherSelected = 5}
                else -> {}
            }
        }
    }



    fun deleteLastElement() {
        _triggerList.removeLast()
    }

    fun addTrigger() {
        _triggerList.add(Weather(0, 0f))
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        for((index, hook) in _triggerList.withIndex()){
            EventChooserElements(hook.weatherPhenomenon, hook.correspondingIntensity)
        }
        Card(elevation = 5.dp, shape = RoundedCornerShape(15.dp), modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(20.dp)) {
                if (_triggerList.size <= 2) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { addTrigger()
                            Log.d("myTag", _triggerList.toString())
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_add_circle_24), contentDescription = "Add Event", modifier = Modifier.scale(1.6f)
                        )
                    }
                }
                if (_triggerList.size >= 2) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { deleteLastElement()
                            Log.d("myTag", _triggerList.toString())
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_delete_forever_24), contentDescription = "Add Event", modifier = Modifier.scale(1.6f)
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.padding(70.dp))
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceEvenly) {

        }
    }
    return _triggerList
}

