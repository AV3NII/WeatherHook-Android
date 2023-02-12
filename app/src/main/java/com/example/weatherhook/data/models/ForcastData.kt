package com.example.weatherhook.data.models

data class ForcastDay (
    val weekDay: String,
    val icon: String,
    val tempLow: Float,
    val tempHigh: Float,
)
data class ForcastData(
    val data: List<ForcastDay>
)