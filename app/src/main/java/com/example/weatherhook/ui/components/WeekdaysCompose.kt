package com.example.weatherhook.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherhook.R


@Composable
fun WeekDaysWidget(daysList: List<String>):String{

    val weekdays = remember { mutableStateOf(daysList)}

    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
        backgroundColor = colorResource(id = R.color.component_background)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
            Text(text = "Days of interest in the Week", color = colorResource(R.color.black_green), fontSize = 20.sp, fontWeight = FontWeight.Bold)
             weekdays.value = Weekdays(daysList = daysList, size = 35, true)


        }

    }
    var daysOfInterest = ""
    for (day in weekdays.value) daysOfInterest += (day + ';')

    return daysOfInterest.removeSuffix(";")
}


@Composable
fun Weekdays(daysList: List<String>, size: Int, clickable: Boolean):List<String> {
    val _daysList = remember { mutableStateOf(daysList) }
    val week = listOf("MO","TU","WE","TH","FR","SA","SU")

    val background = colorResource(id = R.color.light_green_bg)
    val backgroundActivated = colorResource(id = R.color.dark_green)
    val textColor = colorResource(id = R.color.black_green)
    val textColorActivated = colorResource(id = R.color.white)

    var colorColor = Color.Black
    val borderRadius = (size/5)


    Row(modifier = Modifier.padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy((size/4).dp))
    {
        for (day in week){
            Surface(elevation = 3.dp,
                shape = RoundedCornerShape(borderRadius.dp),
                border = BorderStroke(1.5.dp, colorResource(id = R.color.dark_green))
            ) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(size.dp)
                        .width(size.dp)
                        .background(colorResource(id = R.color.light_green_bg))
                        .clickable(onClick = {
                            if (clickable && _daysList.value.contains(day)) {
                                _daysList.value -= day
                            } else if (clickable && !_daysList.value.contains(day)) {
                                _daysList.value += day
                            }
                        })

                        .then(
                            if (_daysList.value.contains(day))
                                Modifier.background(backgroundActivated)
                            else Modifier.background(background)
                        )

                ){

                    if (_daysList.value.contains(day)) colorColor = textColorActivated
                    else colorColor = textColor
                    Text(
                        text = day,
                        textAlign = TextAlign.Center,
                        color = colorColor,
                        fontSize = (size/3).sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }



    }


    return _daysList.value
}


