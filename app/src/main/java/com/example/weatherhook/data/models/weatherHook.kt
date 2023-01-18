package com.example.weatherhook.data.models

data class WeatherHook(
    // val id: Int,     We might include that
    var active: Boolean,
    var title: String,
    //uses float because we don´t need to be nm nor cm precise (Float ~1.7m)
    var location: Pair<Float, Float>,

    var timeToEvent: Int,
    var relevantDays: List<String>,

    var triggers:List<Weather>,

    )

data class Weather (
    val weatherPhenomenon:Int,
    val correspondingIntensity: Any
)

data class WeatherHookList(val events: List<WeatherHook>)