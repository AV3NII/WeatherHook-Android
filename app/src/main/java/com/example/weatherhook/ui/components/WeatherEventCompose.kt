package com.example.weatherhook.ui.components

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
import com.example.weatherhook.data.models.WeatherHookEvent




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
                    Icon(painter = icon[index], contentDescription = "Icon ${index}", Modifier.scale(1.6f), tint = if (weatherSelected == index) {backgroundColorSelected} else backgroundColor)
                }
            }
        }
    }
    return weatherSelected
}


@Composable
fun Elements(weatherPhenomen: Int,_triggerList2: MutableList<Weather>, index: Int): MutableList<Weather> {
    var selectedWeather by remember { mutableStateOf(weatherPhenomen) }
    var _triggerListImage by remember { mutableStateOf(_triggerList2) }
    selectedWeather = Events(_triggerList2[index].weatherPhenomenon)
    Column {
        when (selectedWeather) {
            0 -> {_triggerListImage[index].correspondingIntensity = 1f
                selectedWeather = 0}
            1 -> {_triggerListImage[index].correspondingIntensity = Cloudy(_triggerList2[index].correspondingIntensity)
                selectedWeather = 1}
            2 -> {_triggerListImage[index].correspondingIntensity = SliderSteps(2,steps = 4f, _triggerList2[index].correspondingIntensity)
                selectedWeather = 2}
            3 -> {_triggerListImage[index].correspondingIntensity = SliderSteps(3,steps = 4f, _triggerList2[index].correspondingIntensity)
                selectedWeather = 3}
            4 -> {_triggerListImage[index].correspondingIntensity = SliderSteps(4,steps = 6f, _triggerList2[index].correspondingIntensity)
                selectedWeather = 4}
            5 -> {var temp = Temperature2(_triggerList2[index].correspondingIntensity).toFloatOrNull()
                if (temp == null) {temp = 0f}
                _triggerListImage[index].correspondingIntensity = temp
                selectedWeather = 5}
            else -> {}
        }
    }
    _triggerListImage[index].weatherPhenomenon = selectedWeather
    return _triggerListImage
}


fun deleteLastElement(_triggerList2: MutableList<Weather>): MutableList<Weather> {
    _triggerList2.removeLast()
    return _triggerList2
}


fun addTrigger(_triggerList2: MutableList<Weather>): MutableList<Weather> {
    _triggerList2.add(Weather(0, 0f))
    return _triggerList2
}

@Composable
fun AddDelete(_triggerList2: MutableList<Weather>): MutableList<Weather> {
    var _triggerListImage by remember { mutableStateOf(_triggerList2) }


    Card(elevation = 5.dp, shape = RoundedCornerShape(15.dp), modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(20.dp)) {
            if (_triggerListImage.size <= 2) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                    _triggerListImage = addTrigger(_triggerListImage)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_add_circle_24), contentDescription = "Add Event", modifier = Modifier.scale(1.6f)
                    )
                }
            }
            if (_triggerList2.size >= 2) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { _triggerListImage = deleteLastElement(_triggerListImage)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_delete_forever_24), contentDescription = "Add Event", modifier = Modifier.scale(1.6f)
                    )
                }
            }

        }
    }
    return _triggerListImage
}


@Composable
fun Components(_triggerList2: MutableList<Weather>): MutableList<Weather> {
    var _triggerListImage by remember { mutableStateOf(_triggerList2) }
    var testBoolean = remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        for((index, triggers) in _triggerListImage.withIndex()) {
            _triggerListImage = Elements(triggers.weatherPhenomenon,_triggerListImage, index)
        }

        var testList = AddDelete(_triggerListImage)

        // Try
        if(testList.size != _triggerListImage.size) {
            testBoolean.value = false
        }
        _triggerListImage = testList



    }


    return _triggerListImage
}


@Composable
fun newHook(weatherHookEvent: WeatherHookEvent): MutableList<Weather> {
    var triggerList2 = weatherHookEvent.triggers
    var _triggerListImage by remember { mutableStateOf(triggerList2) }

    _triggerListImage = Components(_triggerListImage)

    return _triggerListImage
}




// Slider without steps -> Cloudy
@Composable
fun Cloudy(percentage: Float): Float {
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
    return sliderPosition
}

// Slider with steps for multi-use
@Composable
fun SliderSteps(weather: Int,steps: Float, stepsSelected: Float): Float {

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
    return sliderPosition
}


// Temp chooser
@Composable
fun Temperature2(temperature: Float): String {
    var textTemp by remember { mutableStateOf(TextFieldValue(temperature.toInt().toString())) }
    var currentText by remember { mutableStateOf(temperature.toInt().toString()) }
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
                    value = textTemp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    onValueChange = {
                        if (it.text.length <= 2) {
                            textTemp = it
                            currentText = it.text
                        }
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
                            innerTextField()
                        }
                    }

                )
            }

            Text(text = "°C", fontSize = 20.sp)
            Text(text = "-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
    if (currentText != null) {
        return currentText
    }
    else return "3"
}