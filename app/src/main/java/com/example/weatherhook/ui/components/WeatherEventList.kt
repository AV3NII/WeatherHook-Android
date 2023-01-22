package com.example.weatherhook.ui.components

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherhook.R
import com.example.weatherhook.data.models.WeatherHookEvent
import com.example.weatherhook.data.models.WeatherHookEventList
import com.example.weatherhook.ui.activities.NewHookActivity


@Composable
fun WeatherHook(event: WeatherHookEvent,context: Context) {
    var active = remember { mutableStateOf(event.active) }

    var icon:Painter = painterResource(id = R.drawable.ic_baseline_anchor_24)
    when(event.triggers[0].weatherPhenomenon){
        0 -> {icon = painterResource(R.drawable.ic_baseline_settings_24)}
        1 -> {icon = painterResource(R.drawable.ic_baseline_anchor_24)}
        2 -> {icon = painterResource(R.drawable.ic_baseline_settings_24)}
        3 -> {icon = painterResource(R.drawable.ic_baseline_settings_24)}
        4 -> {icon = painterResource(R.drawable.ic_baseline_map_24)}
        5 -> {icon = painterResource(R.drawable.ic_baseline_settings_24)}
    }

    return Card(modifier = Modifier
        .padding(10.dp)
        .height(120.dp)
        .clickable {
            Log.d("editEvent", event.toString())
            context.startActivity(Intent(context, NewHookActivity::class.java))
        },
        elevation = 5.dp,
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green))
    ) {
        Row(modifier = Modifier.background(colorResource(id = R.color.light_green_bg))) {
            Surface(shape = RoundedCornerShape(25.dp), modifier = Modifier
                .height(120.dp)
                .width(100.dp),
                border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)))
            {
                Box(modifier = Modifier
                    .background(colorResource(id = R.color.mid_green)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.scale(3f),
                        painter = icon,
                        contentDescription = "Add Event",
                        tint = colorResource(R.color.light_green)
                    )
                }
            }
            Column(modifier = Modifier.padding(start = 5.dp)) {
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
fun WeatherEventList(weatherHookEventList: WeatherHookEventList,context: Context) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding( top = Dp(5f))
        .padding(start = 10.dp, end = 10.dp)
        .verticalScroll(rememberScrollState())) {
            weekDaysWidget(daysList = weatherHookEventList.events[0].relevantDays.split(";"))
        
            weatherHookEventList.events.forEach{

                WeatherHook(event = it, context)

            }
        
        Spacer(modifier = Modifier.padding(45.dp))
    }

}