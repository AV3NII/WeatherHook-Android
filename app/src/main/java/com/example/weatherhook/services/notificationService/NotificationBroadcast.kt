package com.example.weatherhook.services.notificationService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.weatherhook.data.api.Api
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ForecastData
import com.example.weatherhook.data.models.ForecastDay
import com.example.weatherhook.data.repository.ForecastRepo
import com.example.weatherhook.services.compareService.EventChecker
import com.example.weatherhook.services.locationService.LocationService


class NotificationBroadcast() : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        //updates forecast once a day

        val repo = ForecastRepo()
        val db = SQLiteHelper(context)
        val location = LocationService().getLocationPair(context)
        val locationName = LocationService().getName(context, location.first.toDouble(), location.second.toDouble()) ?: "Berlin"
        if (repo.getForecast(db) == ForecastData(listOf<ForecastDay>().toMutableList())){
            Api().callApi(location.first,location.second,7, context) { forecast ->
                if (forecast.cod == "200") {

                    repo.addForecast(forecast, locationName, context, SQLiteHelper(context))
                } else {
                    val test = forecast.city.name
                    Log.e("error", test.toString())
                }
            }
        }else{
            Api().callApi(location.first,location.second,7, context) { forecast ->
                if (forecast.cod == "200") {

                    repo.updateForecast(forecast, locationName, context, SQLiteHelper(context))
                } else {
                    val test = forecast.city.name
                    Log.e("error", test.toString())
                }
            }
        }

        //checks if hooks and forcast align -> send notification

        EventChecker(db,context).makeNotification(intent)


    }


}