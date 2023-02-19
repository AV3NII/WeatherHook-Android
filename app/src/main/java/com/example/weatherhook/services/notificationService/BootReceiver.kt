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
import com.example.weatherhook.services.locationService.LocationService

class BootReceiver : BroadcastReceiver() {
    var didNotRun = true
    override fun onReceive(context: Context, intent: Intent) {
        val repo = ForecastRepo()
        val db = SQLiteHelper(context)
        val location = LocationService().getLocationPair(context)
        val locationName = LocationService().getName(context, location.first.toDouble(), location.second.toDouble()) ?: "Berlin"


        if (intent.action == "android.intent.action.BOOT_COMPLETED" && didNotRun) {
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
                        //Notification(context).scheduleNotification(forecast.city.name, "It is ${(forecast.list[0].temp.max).toInt()-273.15} °C",1)
                    } else {
                        val test = forecast.city.name
                        Log.e("error", test.toString())
                    }
                }
            }
            //Notification(context).scheduleNotification("forecast.city.name", "It is ${(5.5).toInt()-273.15} °C")
            Notification(context).scheduleNotificationManager()
            didNotRun = false
        }


    }
}