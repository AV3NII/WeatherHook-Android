package com.example.weatherhook.services.notificationService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.weatherhook.data.api.callApi

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            callApi(48.208f,12.122f,7, context) { result ->
                if (result.cod == "200") {
                    val test = result.toString()

                    Notification(context).scheduleNotification(result.city.name, "It is ${(result.list[0].deg).toInt()-273.15} °C")
                } else {
                    val test = result.city.name

                }
            }
        }
    }
}