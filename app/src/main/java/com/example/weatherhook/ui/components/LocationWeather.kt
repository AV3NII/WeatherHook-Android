package com.example.weatherhook.ui.components


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherhook.R
import com.example.weatherhook.data.api.IconMapper
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ForecastDay
import com.example.weatherhook.data.repository.ForecastRepo
import kotlin.math.roundToInt


@Composable
fun LocationWeather(context: Context, forecastDay: ForecastDay) {


    val locationName = ForecastRepo().getName(SQLiteHelper(context))

    fun kelvinToCelsius(kelvin: Float):Float{
        return ((kelvin - 273.15) * 10f).roundToInt() /10f
    }

    Surface(modifier = Modifier
        .padding(top = 20.dp)
        .fillMaxWidth()
        ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.background(
            colorResource(id = R.color.white)
        )) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(locationName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(R.color.black_green))

            Row() {
                Icon(painter = IconMapper().mapApiIconToProjectIcon(icon = forecastDay.icon), contentDescription = "", tint = colorResource(R.color.black_green))
                Spacer(modifier = Modifier.width(10.dp))
                Text("${kelvinToCelsius(forecastDay.tempLow)} - ${kelvinToCelsius(forecastDay.tempHigh)}°C", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(R.color.black_green))
            }
            Spacer(modifier = Modifier.width(4.dp))



        }
    }


}