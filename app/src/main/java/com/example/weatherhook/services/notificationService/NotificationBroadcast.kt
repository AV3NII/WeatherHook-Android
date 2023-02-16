package com.example.weatherhook.services.notificationService

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weatherhook.R
import com.example.weatherhook.data.api.Api
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ForecastData
import com.example.weatherhook.data.models.ForecastDay
import com.example.weatherhook.data.repository.ForecastRepo
import com.example.weatherhook.services.locationService.LocationService


const val notificationID = 1
const val channelID = "AlertChannel"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"



class NotificationBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val repo = ForecastRepo()
        val db = SQLiteHelper(context)
        val location = LocationService().getLocationPair(context)

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_baseline_anchor_24)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)

        if (repo.getForecast(context,db) == ForecastData(listOf<ForecastDay>().toMutableList())){
            Api().callApi(location.first,location.second,7, context) { forecast ->
                if (forecast.cod == "200") {
                    repo.addForecast(forecast,context, SQLiteHelper(context))
                    Notification(context).scheduleNotification(forecast.city.name, "It is ${(forecast.list[0].temp.max).toInt()-273.15} °C")
                } else {
                    val test = forecast.city.name
                    Log.e("shit", test.toString())
                }
            }
        }else{
            Api().callApi(location.first,location.second,7, context) { forecast ->
                if (forecast.cod == "200") {
                    repo.updateForecast(forecast,context, SQLiteHelper(context))
                    Notification(context).scheduleNotification(forecast.city.name, "It is ${(forecast.list[0].deg).toInt()-273.15} °C")
                } else {
                    val test = forecast.city.name
                    Log.e("shit", test.toString())
                }
            }
        }

    }
}