package com.example.weatherhook.data.models

data class WeatherHookEvent(
    val eventId: Int,
    var active: Boolean,
    var title: String,

    var location: Pair<Float, Float>,

    var timeToEvent: Int,
    var relevantDays: String,

    var triggers:List<Weather>,

    )

data class Weather (
    val weatherPhenomenon:Int,
    val correspondingIntensity: Float
)

data class WeatherHookEventList(val events: List<WeatherHookEvent>)