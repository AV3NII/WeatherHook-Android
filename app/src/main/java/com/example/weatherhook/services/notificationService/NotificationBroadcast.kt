package com.example.weatherhook.services.notificationService

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.weatherhook.R
import com.example.weatherhook.data.api.callForecastApi


const val notificationID = 1
const val channelID = "AlertChannel"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"



class NotificationBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_baseline_anchor_24)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)

        callForecastApi(48.208f,12.122f,7, context) { result ->
            if (result.cod == "200") {
                val test = result.toString()
            } else {
                val test = result.city.name
            }
        }

    }
}