package com.example.weatherhook.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherhook.R
import com.example.weatherhook.data.models.Weather
import java.util.*

@Composable
fun WeatherForecast(listOfTemp: List<Weather>) {
    val icon = arrayOf(
        painterResource(R.drawable.baseline_wb_sunny_24),
        painterResource(R.drawable.baseline_cloud_24),
        painterResource(R.drawable.baseline_grain_24),
        painterResource(R.drawable.baseline_snowboarding_24),
        painterResource(R.drawable.baseline_wind_power_24),
        painterResource(R.drawable.baseline_whatshot_24),)

    val days = arrayOf(
        "MO", "TU", "WE", "TH", "FR", "SA", "SU"
    )

    val calendar = Calendar.getInstance()
    val current = calendar.get(Calendar.DAY_OF_WEEK)
    var currentDayFormatted = 0
    if (current == 1) currentDayFormatted = 7
    else currentDayFormatted = current - 1
    
    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 5.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
    backgroundColor = colorResource(id = R.color.component_background)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(20.dp)) {

            for ((index, items) in days.withIndex()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(shape = RoundedCornerShape(5.dp), modifier = Modifier.border(2.5.dp, if(currentDayFormatted == index+1)colorResource(
                        id = R.color.mid_green
                    ) else colorResource(id = R.color.dark_green), RoundedCornerShape(5.dp)))
                     {
                        Box(modifier = Modifier
                            .background(colorResource(id = R.color.dark_green))
                            .padding(top = 2.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)) {
                            Text(text = days[index], textAlign = TextAlign.Center, color = colorResource(
                                id = R.color.light_green
                            ))
                        }
                    }


                    Icon(painter = icon[listOfTemp[index].weatherPhenomenon], tint = colorResource(id = R.color.mid_green), contentDescription = "Icon ${index}",
                        modifier = Modifier
                            .scale(1f)
                            .padding(top = 5.dp, bottom = 5.dp))

                    Text(text = "${listOfTemp[index].correspondingIntensity.toInt()} °C", textAlign = TextAlign.Center, fontSize = 12.sp)
                }


            }


        }
    }
}