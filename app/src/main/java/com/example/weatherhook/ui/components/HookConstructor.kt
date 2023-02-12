package com.example.weatherhook.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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

    val backgroundColor = colorResource(id = R.color.light_green)
    val backgroundColorSelected = colorResource(id = R.color.black_green)
    val icon = arrayOf(painterResource(id = R.drawable.baseline_wb_sunny_24),
        painterResource(id = R.drawable.baseline_cloud_24),
        painterResource(R.drawable.baseline_grain_24),
        painterResource(R.drawable.baseline_snowboarding_24),
        painterResource(R.drawable.baseline_wind_power_24),
        painterResource(R.drawable.baseline_whatshot_24))
    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(40.dp)) {
            for ((index) in icon.withIndex()) {
                IconButton(onClick = {weatherSelected = index}, modifier = Modifier.size(24.dp)) {
                    Icon(painter = icon[index], contentDescription = "Icon $index", Modifier.scale(1.6f), tint = if (weatherSelected == index) {backgroundColorSelected} else backgroundColor)
                }
            }
        }
    }
    return weatherSelected
}


@Composable
fun Elements(weatherPhenomen: Int,_triggerList: MutableList<Weather>, index: Int): MutableList<Weather> {
    var selectedWeather by remember { mutableStateOf(weatherPhenomen) }
    val _triggerListImage by remember { mutableStateOf(_triggerList) }

    selectedWeather = Events(_triggerList[index].weatherPhenomenon)
    Column {
        when (selectedWeather) {
            0 -> {_triggerListImage[index].correspondingIntensity = 1f
                }
            1 -> {val data = Cloudy(_triggerList[index].correspondingIntensity, _triggerList[index].checkMoreThan)
                _triggerListImage[index].correspondingIntensity = data.first
                _triggerListImage[index].checkMoreThan = data.second
                }
            2 -> {val data = SliderSteps(2,steps = 4f, _triggerList[index].correspondingIntensity, _triggerList[index].checkMoreThan)
                _triggerListImage[index].correspondingIntensity = data.first
                _triggerListImage[index].checkMoreThan = data.second
                }
            3 -> {val data = SliderSteps(3,steps = 4f, _triggerList[index].correspondingIntensity, _triggerList[index].checkMoreThan)
                _triggerListImage[index].correspondingIntensity = data.first
                _triggerListImage[index].checkMoreThan = data.second
                }
            4 -> {val data = SliderSteps(4,steps = 6f, _triggerList[index].correspondingIntensity, _triggerList[index].checkMoreThan)
                _triggerListImage[index].correspondingIntensity = data.first
                _triggerListImage[index].checkMoreThan = data.second
                }
            5 -> {val data = Temperature(_triggerList[index].correspondingIntensity,_triggerList[index].checkMoreThan)
                var temp = data.first.toFloatOrNull()
                if (temp == null) {temp = 0f}//should never be executed
                _triggerListImage[index].correspondingIntensity = temp
                _triggerListImage[index].checkMoreThan = data.second
            }

            else -> {}
        }
    }
    _triggerListImage[index].weatherPhenomenon = selectedWeather
    return _triggerListImage
}


fun deleteLastElement(_triggerList: MutableList<Weather>): MutableList<Weather> {
    _triggerList.removeLast()
    return _triggerList
}


fun addTrigger(_triggerList: MutableList<Weather>): MutableList<Weather> {
    _triggerList.add(Weather(0, 0f,true))
    return _triggerList
}

@Composable
fun AddDelete(_triggerList: MutableList<Weather>): MutableList<Weather> {
    var _triggerListImage by remember { mutableStateOf(_triggerList) }
    var refreshBoolean by remember { mutableStateOf(false) }

    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(20.dp)) {
            if (_triggerListImage.size <= 2) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { _triggerListImage = addTrigger(_triggerListImage)
                    refreshBoolean = !refreshBoolean}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_add_circle_24), contentDescription = "Add Event", modifier = Modifier.scale(1.6f),
                        tint = colorResource(R.color.black_green)
                    )
                }
            }
            if (_triggerList.size >= 2) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { _triggerListImage = deleteLastElement(_triggerListImage)
                        refreshBoolean = !refreshBoolean
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_delete_forever_24), contentDescription = "Add Event", modifier = Modifier.scale(1.6f),
                        tint = colorResource(R.color.black_green)
                    )
                }
            }

        }
    }
    if (refreshBoolean) Card() {  }

    return _triggerListImage
}


@Composable
fun Components(_triggerList: MutableList<Weather>): MutableList<Weather> {
    var _triggerListImage by remember { mutableStateOf(_triggerList) }

    Column() {
        for((index, triggers) in _triggerListImage.withIndex()) {
            _triggerListImage = Elements(triggers.weatherPhenomenon,_triggerListImage, index)
        }

        _triggerListImage = AddDelete(_triggerListImage)

    }

    return _triggerListImage
}


@Composable
fun Hooks(weatherHookEvent: WeatherHookEvent): MutableList<Weather> {
    val triggerList = weatherHookEvent.triggers
    var _triggerListImage by remember { mutableStateOf(triggerList) }

    _triggerListImage = Components(_triggerListImage)

    return _triggerListImage
}




// Slider without steps -> Cloudy
@Composable
fun Cloudy(percentage: Float,checkMoreThan:Boolean): Pair<Float,Boolean> {
    var sliderPosition by remember { mutableStateOf(percentage) }
    var _checkMoreThan by remember { mutableStateOf(checkMoreThan) }

    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)) {
        Row(modifier = Modifier
            .height(80.dp)
            .padding(30.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(painter = painterResource(R.drawable.baseline_cloud_24), contentDescription = "Cloud", modifier = Modifier.scale(1.6f), tint = colorResource(R.color.black_green))
            Spacer(modifier = Modifier.width(20.dp))
            if (_checkMoreThan){
                IconButton(onClick = { _checkMoreThan = false}) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_up_24),
                        contentDescription = "checkAbove",
                        modifier = Modifier.scale(2.5f),
                        tint = colorResource(R.color.light_green)
                    )
                }
            }else{
                IconButton(onClick = { _checkMoreThan = true}) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = "checkBeneath",
                        modifier = Modifier.scale(2.5f),
                        tint = colorResource(R.color.light_green)
                    )
                }
            }

            Spacer(modifier = Modifier.width(5.dp))
            Slider(
                value = sliderPosition,
                valueRange = 0f..1f,
                onValueChange = { sliderPosition = it },
                colors = SliderDefaults.colors(thumbColor = colorResource(R.color.dark_green), activeTrackColor = colorResource(
                    R.color.mid_green), inactiveTrackColor =  colorResource(R.color.white)),
                modifier = Modifier
                    .width(250.dp)
            )

        }
    }

    return Pair<Float,Boolean>(sliderPosition,_checkMoreThan)
}

// Slider with steps for multi-use
@Composable
fun SliderSteps(weather: Int,steps: Float, stepsSelected: Float,checkMoreThan:Boolean): Pair<Float,Boolean> {

    // 100% -> SliderSteps(n, n-1)

    val icon = arrayOf(painterResource(R.drawable.baseline_grain_24),
        painterResource(R.drawable.baseline_snowboarding_24),
        painterResource(R.drawable.baseline_wind_power_24))
    var sliderPosition by remember { mutableStateOf(stepsSelected) }
    var _checkMoreThan by remember { mutableStateOf(checkMoreThan) }
    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)) {
        Row(modifier = Modifier
            .height(80.dp)
            .padding(30.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(painter = icon[weather-2], contentDescription = "Cloud", modifier = Modifier.scale(1.6f), tint = colorResource(R.color.dark_green))
            Spacer(modifier = Modifier.width(20.dp))
            if (_checkMoreThan){
                IconButton(onClick = { _checkMoreThan = false}) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_up_24),
                        contentDescription = "checkAbove",
                        modifier = Modifier.scale(2.5f),
                        tint = colorResource(R.color.light_green)
                    )
                }
            }else{
                IconButton(onClick = { _checkMoreThan = true}) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = "checkBeneath",
                        modifier = Modifier.scale(2.5f),
                        tint = colorResource(R.color.light_green)
                    )
                }
            }

            Spacer(modifier = Modifier.width(5.dp))
            Slider(value = sliderPosition, onValueChange = { sliderPosition = it }, colors = SliderDefaults.colors(thumbColor = colorResource(R.color.dark_green), activeTrackColor = colorResource(
                R.color.mid_green), inactiveTrackColor =  colorResource(R.color.white)), steps = (steps.toInt()-2), modifier = Modifier.width(270.dp))
        }
    }
    return Pair<Float,Boolean>(sliderPosition,_checkMoreThan)
}


// Temp chooser
@Composable
fun Temperature(temperature: Float,checkMoreThan:Boolean): Pair<String,Boolean> {
    var textTemp by remember { mutableStateOf(TextFieldValue(temperature.toInt().toString())) }
    var currentText by remember { mutableStateOf(temperature.toInt().toString()) }
    var _checkMoreThan by remember { mutableStateOf(checkMoreThan) }

    fun isNumeric(toCheck: String): Boolean {
        if (toCheck == ""){return true}
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return toCheck.matches(regex)
    }

    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)) {
        Row(modifier = Modifier
            .height(80.dp)
            .padding(start = 30.dp, end = 30.dp, top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.baseline_whatshot_24), contentDescription = "temp", modifier = Modifier.scale(1.6f), tint = colorResource(id = R.color.dark_green))
            Spacer(modifier = Modifier.width(130.dp))
            if (_checkMoreThan){
                IconButton(onClick = { _checkMoreThan = false}) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_up_24),
                        contentDescription = "checkAbove",
                        modifier = Modifier.scale(2.5f),
                        tint = colorResource(R.color.light_green)
                    )
                }
            }else{
                IconButton(onClick = { _checkMoreThan = true}) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = "checkBeneath",
                        modifier = Modifier.scale(2.5f),
                        tint = colorResource(R.color.light_green)
                    )
                }
            }
            Surface(modifier = Modifier.drawWithContent {
                drawContent()
                val strokeWidth = 5f
                val y = size.height - 10f
                drawLine(
                    color = Color(R.color.black_green),
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }) {
                BasicTextField(
                    singleLine = true,
                    value = textTemp,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),

                    onValueChange = {
                        if (isNumeric(it.text) && it.text.length<= 2) {
                            textTemp = it
                            currentText = it.text
                        }
                    },

                    modifier = Modifier
                        .width(60.dp)
                        .height(40.dp)
                        .background(color = colorResource(id = R.color.component_background))
                        .fillMaxSize(),

                    textStyle = TextStyle.Default.copy(
                        fontSize = 27.sp,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.black_green)
                    ),


                )
            }

            Text(text = "°C", fontSize = 20.sp)
        }
    }
    if (currentText != "" || currentText !=null) {
        return Pair<String,Boolean>(currentText,_checkMoreThan)
    }
    else return Pair<String,Boolean>("0",_checkMoreThan)
}