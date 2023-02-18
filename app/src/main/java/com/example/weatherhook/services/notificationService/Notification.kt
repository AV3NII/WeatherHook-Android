package com.example.weatherhook.services.notificationService

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Notification(private val context: Context) {

    companion object{
        const val channelID = "HookChannel"
    }
    var companion = Companion
    fun scheduleNotificationManager() {
        val intent = Intent(context, NotificationBroadcast::class.java)

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val time = getTime()

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule the notification to be sent after device boot
        val bootIntent = Intent(context, BootReceiver::class.java)
        val bootPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            bootIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, bootPendingIntent)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            bootPendingIntent
        )

        //Schedules our EventChecker
        //interval:        AlarmManager.INTERVAL_DAY

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 60000, pendingIntent)


    }

    private fun getTime() : Long {
        val myDateString = "11:00:15"
        val sdf = SimpleDateFormat("HH:mm:ss")
        val date = sdf.parse(myDateString)

        val calendar = Calendar.getInstance() // gets a calendar using the default time zone and locale.

        var time = Date().time
        time += 10000

        calendar.setTime(date)

        return  calendar.timeInMillis

    }
}

