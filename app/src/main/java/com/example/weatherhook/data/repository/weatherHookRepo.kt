package com.example.weatherhook.data.repository

import android.util.Log
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.models.WeatherHook
import com.example.weatherhook.data.models.WeatherHookList
import com.google.gson.Gson
import java.io.File


class WeatherHookRepo {
    fun loadAllData(): WeatherHookList{
        return try {
            val gson = Gson()
            val jsonString = File("../data.json").readText()
            Log.d("myTag", jsonString)
            gson.fromJson(jsonString, WeatherHookList::class.java)
        } catch (e: Exception) {
            Log.e("DataRepository", "Error loading json data", e)
            return WeatherHookList( listOf(WeatherHook(active = false, title = "Error", location = Pair(0.2f,0.3f), timeToEvent = 2, relevantDays = listOf("Monday"), triggers = listOf(
                Weather(weatherPhenomenon = 2, correspondingIntensity = 2)) )
            )
            )
        }
    }
}