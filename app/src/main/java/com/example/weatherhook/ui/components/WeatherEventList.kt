package com.example.weatherhook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherhook.R
import com.example.weatherhook.data.models.WeatherHookEvent
import com.example.weatherhook.data.models.WeatherHookEventList


@Composable
fun WeatherHook(event: WeatherHookEvent) {
    var active = remember { mutableStateOf(event.active) }


    return Card(modifier = Modifier
        .padding(10.dp)
        .height(120.dp), elevation = 5.dp, shape = RoundedCornerShape(15.dp)
    ) {
        Row {
            Surface(shape = RoundedCornerShape(15.dp), modifier = Modifier
                .height(120.dp)
                .width(100.dp)) {
                Box(modifier = Modifier.background(colorResource(id = R.color.mid_green))) {
                }
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(text = event.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 10.dp))
                Text(text = "Time to event: ${event.timeToEvent} Day(s)", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                Weekdays(event.relevantDays.split(";"), 20)
            }
            Column(modifier = Modifier.padding(end = 20.dp)) {
                Switch(modifier = Modifier
                    .fillMaxSize()
                    .scale(1.3f), checked = active.value, onCheckedChange = {active.value = it})
            }


        }
    }
}

@Composable
fun WeatherEventList(weatherHookEventList: WeatherHookEventList) {
    Column(modifier = Modifier.fillMaxWidth().padding(end = Dp(20f))
        .verticalScroll(rememberScrollState())) {
            weatherHookEventList.events.forEach{

                WeatherHook(event = it)

            }
        
        Box(modifier = Modifier.size(Dp(20f), Dp(65f)))
    }

}