package com.example.weatherhook.services.notificationService

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Notification(private val context: Context) {

    fun scheduleNotification(title: String, message: String) {
        val intent = Intent(context, NotificationBroadcast::class.java)


        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        val alarmManager2 = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager





        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Schedule the notification to be sent after device boot
        val bootIntent = Intent(context, BootReceiver::class.java)
        val bootPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            bootIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


//  Send an Alarm right away at phone restart



        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            bootPendingIntent
        )




        // Set an alarmManager at phone restart -> each 3 hours
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 6000, bootPendingIntent)


        // Set an alarmManager at app start -> each 3 hours
        //TODO: this doesn´t work
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, time, 6000, pendingIntent)


    }

    private fun getTime() : Long {
        val myDateString = "7:00:15"
        val sdf = SimpleDateFormat("HH:mm:ss")
        val date = sdf.parse(myDateString)

        val calendar = Calendar.getInstance() // gets a calendar using the default time zone and locale.

        var time = Date().time
        time += 10000
        //calendar.add(Calendar.SECOND, 20)

        calendar.setTime(date)

        return  calendar.timeInMillis
        //return time
    }
}