package com.example.weatherhook.data.models

data class WeatherHookEvent(
    val eventId: Int,
    var active: Boolean,
    var title: String,

    var location: Pair<Float, Float>,

    var timeToEvent: Int,
    var relevantDays: String,

    var triggers:MutableList<Weather>,

    )

data class Weather (
    var weatherPhenomenon:Int,
    var correspondingIntensity: Float
)

data class WeatherHookEventList(val events: MutableList<WeatherHookEvent>)