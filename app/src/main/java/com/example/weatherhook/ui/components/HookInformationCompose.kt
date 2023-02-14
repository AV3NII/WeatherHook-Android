package com.example.weatherhook.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherhook.R
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.models.WeatherHookEvent
import com.example.weatherhook.data.repository.DatabaseRepo
import com.example.weatherhook.ui.activities.MainActivity

@Composable
fun HookName(eventName: String):String {
    val textState = remember { mutableStateOf(TextFieldValue(eventName)) }
    Card(
        elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(15.dp)
        ) {
            Icon(
                painterResource(R.drawable.baseline_drive_file_rename_outline_24),
                contentDescription = "Name Icon",
                tint = colorResource(id = R.color.dark_green)
            )
            Spacer(modifier = Modifier.width(10.dp))
            BasicTextField(
                value = textState.value,
                onValueChange = { if (it.text.length <= 20) textState.value = it },
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 17.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.black_green)),
            )
        }
    }
    return textState.value.text
}

@Composable
fun TimeToEvent(daysToEvent: Int):Int {
    //var sliderPosition = daysToEvent.toFloat()-1
    var sliderPosition by remember { mutableStateOf(daysToEvent.toFloat()-1) }
    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
            Text("Time to Event in days", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = colorResource(
                id = R.color.black_green
            ))
            Spacer(modifier = Modifier.height(15.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)) {
                Text(text = "1")
                Text(text = "2")
                Text(text = "3")
                Text(text = "4")
                Text(text = "5")
            }
            Slider(value = sliderPosition, enabled = true, steps = 3, onValueChange = { sliderPosition = it }, valueRange = 0f..4f,
                colors = SliderDefaults.colors(thumbColor = colorResource(R.color.black_green),
                    activeTrackColor = colorResource(R.color.light_green),
                    inactiveTrackColor = colorResource(R.color.white)
                ))
        }

    }
    return sliderPosition.toInt()
}

@Composable
fun SaveAndDelete(weatherHookEvent: WeatherHookEvent, context: Context, db: SQLiteHelper) {
    val lineColor = colorResource(R.color.black_green)
    Surface(color = colorResource(R.color.white), modifier = Modifier
        .fillMaxWidth()
        .drawBehind {
            drawLine(
                color = lineColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 10.dp.toPx()
            )
        }
        .height(80.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
//TODO: Create delete and hook it up
                }, modifier = Modifier.width(150.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.signal_red))
            ) {
                Text(text = "Delete", color = colorResource(R.color.white), fontSize = 15.sp)
            }

            Button(
                onClick = {
                    if (weatherHookEvent.eventId == -2) {
                        DatabaseRepo()
                            .addNewWeatherHookToDb(
                                weatherHookEvent,
                                context,
                                db
                            )
                    }else if (weatherHookEvent.eventId == -1){
                        Toast.makeText(context,"Something went wrong (ID = -1)",Toast.LENGTH_LONG).show()
                    }else{
                        DatabaseRepo()
                            .updateEvent(
                                weatherHookEvent,
                                context,
                                db
                            )
                    }
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)

                }, modifier = Modifier.width(150.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.dark_green))
            ) {
                Text(text = "Save", color = colorResource(R.color.white), fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun HookInformation(weatherHookEvent: WeatherHookEvent, context:Context, db: SQLiteHelper ):WeatherHookEvent {

    var pos = Pair(0.0f,0.0f)
    var name = ""
    var timeToEvent = -1
    var relevantDays = ""
    var triggers = listOf(Weather(-1,0.0f,false)).toMutableList()

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.fillMaxHeight(.90f)) {
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())) {
                pos = MapsHook(posLat = 52.520008, posLong = 13.404954)
                Spacer(modifier = Modifier.height(15.dp))
                name = HookName(weatherHookEvent.title)
                timeToEvent = TimeToEvent(weatherHookEvent.timeToEvent)
                relevantDays = WeekDaysWidget(daysList = weatherHookEvent.relevantDays.split(";"))
                triggers = Hooks(weatherHookEvent = weatherHookEvent)

            }
        }
                weatherHookEvent.location = pos
                weatherHookEvent.title = name
                weatherHookEvent.timeToEvent = timeToEvent
                weatherHookEvent.relevantDays = relevantDays
                weatherHookEvent.triggers = triggers

        SaveAndDelete(weatherHookEvent,context,db)
    }

    return weatherHookEvent
}