package com.example.weatherhook.data.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName


data class ApiData (
    val city: City,
    val cod: String,
    val message: Double,
    val cnt: Long,
    val list: List<ListElement>
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): ApiData {
            return Gson().fromJson(json, ApiData::class.java)
        }
    }
}

data class City (
    val id: Long,
    var name: String,
    val coord: Coord,
    val country: String,
    val population: Long,
    val timezone: Long
)

data class Coord (
    val lon: Double,
    val lat: Double
)

data class ListElement (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,

    @SerializedName("feels_like")
    val feelsLike: FeelsLike,

    val pressure: Long,
    val humidity: Long,
    val weather: List<ApiWeather>,
    val speed: Double,
    val deg: Long,
    val gust: Double,
    val clouds: Long,
    val pop: Double,
    val rain: Double? = null,
    val snow: Double? = null
)

data class FeelsLike (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp (
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class ApiWeather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)