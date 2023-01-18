package com.example.weatherhook.data.repository

import android.util.Log
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.models.WeatherHook
import com.example.weatherhook.data.models.WeatherHookList
import com.google.gson.Gson

class WeatherHookRepo {
    fun loadAllData(): WeatherHookList{
        return try {
            val gson = Gson()
            val jsonString = jsonData
            gson.fromJson(jsonString, WeatherHookList::class.java)
        } catch (e: Exception) {
            Log.e("DataRepository", "Error loading json data", e)
            return WeatherHookList( listOf(WeatherHook(active = false, title = "Error", location = Pair(0.2f,0.3f), timeToEvent = 2, relevantDays = listOf("MO"), triggers = listOf(
                Weather(weatherPhenomenon = 2, correspondingIntensity = 2)) )
            )
            )
        }
    }

    private val jsonData = """{
  "events": [
    {
      "active": false,
      "title": "Swimming",
      "location": {
        "x": 50.0,
        "y": 75.0
      },
      "timeToEvent": 3,
      "relevantDays": [
        "SA",
        "SU"
      ],
      "triggers": [
        {
          "weatherPhenomenon": 0,
          "correspondingIntensity": false
        }
      ]
    },
    {
      "active": true,
      "title": "Climbing",
      "location": {
        "x": 50.0,
        "y": 75.0
      },
      "timeToEvent": 3,
      "relevantDays": [
        "MO",
        "TU",
        "WE",
        "TH",
        "FR"
      ],
      "triggers": [
        {
          "weatherPhenomenon": 1,
          "correspondingIntensity": 0.5
        },
        {
          "weatherPhenomenon": 5,
          "correspondingIntensity": 18
        }
      ]
    },
    {
      "active": true,
      "title": "Surfing",
      "location": {
        "x": 50.0,
        "y": 75.0
      },
      "timeToEvent": 5,
      "relevantDays": [
        "MO",
        "TU",
        "WE",
        "TH",
        "FR",
        "SA",
        "SU"
      ],
      "triggers": [
        {
          "weatherPhenomenon": 4,
          "correspondingIntensity": 3
        },
        {
          "weatherPhenomenon": 5,
          "correspondingIntensity": 20
        }
      ]
    },
    {
      "active": false,
      "title": "Jogging",
      "location": {
        "x": 50.0,
        "y": 75.0
      },
      "timeToEvent": 1,
      "relevantDays": [
        "MO",
        "TU",
        "WE",
        "TH",
        "FR"
      ],
      "triggers": [
        {
          "weatherPhenomenon": 1,
          "correspondingIntensity": 0.7
        },
        {
          "weatherPhenomenon": 2,
          "correspondingIntensity": 0.33
        }
      ]
    }
  ]
}"""
}