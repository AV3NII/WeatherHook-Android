package com.example.weatherhook.data.models

data class ForecastDay (
    val weekDay: String,
    val icon: String,
    val tempLow: Float,
    val tempHigh: Float,
)
data class ForecastData(
    val data: MutableList<ForecastDay>
)