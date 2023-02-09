package com.example.weatherhook.data.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherhook.BuildConfig
import com.example.weatherhook.data.models.*
import com.google.gson.Gson

val apiKey = BuildConfig.OPEN_WEATHER_API_KEY

fun callApi(lat: Float, lon: Float, cnt: Int, context: Context, callback: (ApiData) -> Unit) {
    val gson = Gson()
    var changedBoolean = false

    // tag:callAPI tag:APITest tag:trycatchAPI tag:test tag:gson

    // https://api.openweathermap.org/data/2.5/forecast/daily?lat=52.520008&lon=13.404954&exclude=hourly&appid=

    val baseUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?"
    val url = baseUrl + "lat=${lat}&lon=${lon}&cnt=${cnt}&exclude=hourly&appid=${apiKey}"


    val queue = Volley.newRequestQueue(context)

    var apiData = ApiData(city = City(id = 100, name = "ERROR", coord = Coord(lon = 12.34, lat = 56.78), country = "ERROR", population = 1000000, timezone = 3600),cod = "600",message = 0.01,cnt = 5, list = listOf(ListElement(dt = 1623476760, sunrise = 1623476760, sunset = 1623476760, temp = Temp(day = 20.0, min = 15.0, max = 25.0, night = 10.0, eve = 18.0, morn = 12.0), feelsLike = FeelsLike(day = 19.0, night = 11.0, eve = 17.0, morn = 13.0), pressure = 1000, humidity = 70, weather = listOf(ApiWeather(id = 800, main = "Clear", description = "clear sky", icon = "01d")), speed = 2.0, deg = 180, gust = 3.0, clouds = 20, pop = 0.1, rain = 0.2, snow = 0.3)))


    // Request a string response from the provided URL.
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            // Display the first 500 characters of the response string.
            //Log.d("callAPI", "Response is: ${response.substring(0, 500)}")


            try {

                apiData = Gson().fromJson(response, ApiData::class.java)
                //apiData = ApiData(city = City(id = 200, name = "GSONWORKED", coord = Coord(lon = 12.34, lat = 56.78), country = "ERROR", population = 1000000, timezone = 3600),cod = "600",message = 0.01,cnt = 5, list = listOf(ListElement(dt = 1623476760, sunrise = 1623476760, sunset = 1623476760, temp = Temp(day = 20.0, min = 15.0, max = 25.0, night = 10.0, eve = 18.0, morn = 12.0), feelsLike = FeelsLike(day = 19.0, night = 11.0, eve = 17.0, morn = 13.0), pressure = 1000, humidity = 70, weather = listOf(ApiWeather(id = 800, main = "Clear", description = "clear sky", icon = "01d")), speed = 2.0, deg = 180, gust = 3.0, clouds = 20, pop = 0.1, rain = 0.2, snow = 0.3)))
                Log.d("gson", apiData.city.name)
                Log.d("trycatchAPI", "Try opened")
                callback(apiData)
            } catch (e: Exception) {

                // handle the exception and take appropriate action
                apiData = ApiData(city = City(id = 300, name = "GSONDIDNTWORK", coord = Coord(lon = 12.34, lat = 56.78), country = "ERROR", population = 1000000, timezone = 3600),cod = "600",message = 0.01,cnt = 5, list = listOf(ListElement(dt = 1623476760, sunrise = 1623476760, sunset = 1623476760, temp = Temp(day = 20.0, min = 15.0, max = 25.0, night = 10.0, eve = 18.0, morn = 12.0), feelsLike = FeelsLike(day = 19.0, night = 11.0, eve = 17.0, morn = 13.0), pressure = 1000, humidity = 70, weather = listOf(ApiWeather(id = 800, main = "Clear", description = "clear sky", icon = "01d")), speed = 2.0, deg = 180, gust = 3.0, clouds = 20, pop = 0.1, rain = 0.2, snow = 0.3)))
                Log.d("gson", apiData.city.toString())
                Log.d("trycatchAPI", "Catch opened")
                callback(apiData)
            }
        },
        {

            Log.d("callAPI", "That didn't work!")

        })


    queue.add(stringRequest)
}

