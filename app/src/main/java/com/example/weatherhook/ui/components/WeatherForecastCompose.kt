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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherhook.R
import com.example.weatherhook.data.api.IconMapper
import com.example.weatherhook.data.models.ForcastData
import java.util.*

@Composable
fun WeatherForecast(forcastData: ForcastData) {

    val mapper = IconMapper()

    val days = arrayOf(
        "MO", "TU", "WE", "TH", "FR", "SA", "SU"
    )

    val calendar = Calendar.getInstance()
    val current = calendar.get(Calendar.DAY_OF_WEEK)
    val currentDayFormatted = if (current == 1) 7 else  current - 1


    fun kelvinToCelsius(kelvin: Float):Float{
        return Math.round((kelvin - 273.15)*10f)/10f
    }

    Card(elevation = 5.dp, shape = RoundedCornerShape(25.dp), modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 5.dp)
        .fillMaxWidth(),
        border = BorderStroke(1.5.dp, colorResource(id = R.color.black_green)),
    backgroundColor = colorResource(id = R.color.component_background)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(20.dp)) {

            for (( index, day) in days.withIndex()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .border(
                                3.dp,
                                if (currentDayFormatted == index + 1) colorResource(id = R.color.mid_green)
                                else colorResource(id = R.color.dark_green),
                                RoundedCornerShape(5.dp)
                            )
                    )
                     {
                        Box(modifier = Modifier
                            .background(
                                if (currentDayFormatted == index + 1) colorResource(id = R.color.mid_green)
                                else colorResource(
                                    id = R.color.dark_green
                                ), RoundedCornerShape(5.dp)
                            )
                            .padding(top = 2.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)) {
                            if (currentDayFormatted == index + 1){
                                Text(text = day, textAlign = TextAlign.Center,fontWeight = FontWeight.Bold, color = colorResource(id = R.color.black_black))
                            }else{
                                Text(text = day, textAlign = TextAlign.Center,fontWeight = FontWeight.Normal, color = colorResource(id = R.color.light_green))
                            }

                        }
                    }


                    Icon(painter = mapper.mapApiIconToProjectIcon(forcastData.data.find { it.weekDay == days[index] }?.icon ?:"error"), tint = colorResource(id = R.color.mid_green), contentDescription = "Icon ${forcastData.data[index].icon}",
                        modifier = Modifier
                            .scale(1f)
                            .padding(top = 5.dp, bottom = 5.dp))

                    Text(text = "${kelvinToCelsius(forcastData.data.find { it.weekDay == days[index] }!!.tempHigh)} °C", textAlign = TextAlign.Center, fontSize = 12.sp,color = colorResource(
                                id = R.color.dark_green
                            ))
                    Text(text = "${kelvinToCelsius(forcastData.data.find { it.weekDay == days[index] }!!.tempLow)} °C", textAlign = TextAlign.Center, fontSize = 12.sp ,color = colorResource(
                                id = R.color.mid_green
                            ))
                }

            }


        }
    }
}