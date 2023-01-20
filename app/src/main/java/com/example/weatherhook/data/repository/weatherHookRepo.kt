package com.example.weatherhook.data.repository

import android.util.Log
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.models.WeatherHookEvent
import com.example.weatherhook.data.models.WeatherHookEventList
import com.google.gson.Gson

class WeatherHookRepo {
    fun loadAllData(): WeatherHookEventList {
        return try {
            val gson = Gson()
            val jsonString = jsonData
            gson.fromJson(jsonString, WeatherHookEventList::class.java)
        } catch (e: Exception) {
            Log.e("DataRepository", "Error loading json data", e)
            return WeatherHookEventList( listOf(WeatherHookEvent(eventId = 0, active = false, title = "Error", location = Pair(0.2f,0.3f), timeToEvent = 2, relevantDays = "MO", triggers = listOf(Weather(weatherPhenomenon = 2, correspondingIntensity = 2f)))
            )
            )
        }
    }

    private val jsonData = """{
  "events": [
    {
      "eventId":0,
      "active": false,
      "title": "Swimming",
      "location": {
        "x": 50.0,
        "y": 75.0
      },
      "timeToEvent": 3,
      "relevantDays": "SA;SO",
      "triggers": [
        {
          "weatherPhenomenon": 0,
          "correspondingIntensity": 0f
        }
      ]
    },
    {
      "eventId":1,
      "active": true,
      "title": "Climbing",
      "location": {
        "x": 50.0,
        "y": 75.0
      },
      "timeToEvent": 3,
      "relevantDays": "MO;TU;WE;TH;FR",
      "triggers": [
        {
          "weatherPhenomenon": 1,
          "correspondingIntensity": 0.5f
        },
        {
          "weatherPhenomenon": 5,
          "correspondingIntensity": 18f
        }
      ]
    },
    {
      "eventId":2,
      "active": true,
      "title": "Surfing",
      "location": {
        "x": 50.0,
        "y": 75.0
      },
      "timeToEvent": 5,
      "relevantDays": "MO;TU;WE;TH;FR;SA;SU",
      "triggers": [
        {
          "weatherPhenomenon": 4,
          "correspondingIntensity": 3f
        },
        {
          "weatherPhenomenon": 5,
          "correspondingIntensity": 20f
        }
      ]
    },
    {
      "eventId":3,
      "active": false,
      "title": "Jogging",
      "location": {
        "x": 50.0,
        "y": 75.0
      },
      "timeToEvent": 1,
      "relevantDays": "MO;TU;WE;TH;FR",
      "triggers": [
        {
          "weatherPhenomenon": 1,
          "correspondingIntensity": 0.7f
        },
        {
          "weatherPhenomenon": 2,
          "correspondingIntensity": 1f
        }
      ]
    }
  ]
}"""
}