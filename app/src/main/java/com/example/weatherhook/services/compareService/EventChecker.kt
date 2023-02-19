package com.example.weatherhook.services.compareService

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weatherhook.R
import com.example.weatherhook.data.api.Api
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ListElement
import com.example.weatherhook.data.repository.EventRepo
import com.example.weatherhook.services.notificationService.Notification.Companion.channelID
import com.example.weatherhook.ui.activities.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class EventChecker(db: SQLiteHelper, context: Context) {
    private val eventRepo = EventRepo()
    private val _context = context

    private val allEvents = eventRepo.getAllEvents(db)

    private fun getDayString(dt:Long):String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val date = Date(dt * 1000)
        val selectedDate = sdf.format(date)

        return when (selectedDate){
            "Monday" -> "MO"
            "Tuesday" -> "TU"
            "Wednesday" -> "WE"
            "Thursday" -> "TH"
            "Friday" -> "FR"
            "Saturday" -> "SA"
            "Sunday" -> "SU"
            else -> ""
        }
    }

    fun makeNotification(intent: Intent){


        allEvents.events.forEachIndexed{ index, event ->
            lateinit var relevantWeather: ListElement
            if (event.active){
                Api().callApi(event.location.first,event.location.second,6, _context) { forecast ->
                    if (forecast.cod == "200") {
                        relevantWeather = forecast.list[event.timeToEvent]

                        if (event.relevantDays.split(";").contains(getDayString(relevantWeather.dt))){

                            val triggeredTriggers = listOf<Boolean>().toMutableList()

                            event.triggers.forEach { trigger->
                                if (trigger.checkMoreThan){

                                    when(trigger.weatherPhenomenon){
                                        0 -> if (relevantWeather.clouds.toInt() <= 20){
                                            triggeredTriggers.add(true)
                                        }else triggeredTriggers.add(false)

                                        1 -> if (relevantWeather.clouds >= (trigger.correspondingIntensity * 100).toLong()){
                                            triggeredTriggers.add(true)
                                        }else triggeredTriggers.add(false)

                                        2 -> {
                                            if (relevantWeather.rain == null) {
                                                relevantWeather.rain = 0.00
                                            }
                                            else{
                                                when (trigger.correspondingIntensity){
                                                    0.00.toFloat() -> trigger.correspondingIntensity = 0.00f
                                                    0.33333334.toFloat() -> trigger.correspondingIntensity = 7.00f
                                                    0.6666666.toFloat() -> trigger.correspondingIntensity = 14.00f
                                                    1f -> trigger.correspondingIntensity = 21.00f
                                                }
                                            }
                                            if (relevantWeather.rain!! >= trigger.correspondingIntensity.toDouble()){
                                                triggeredTriggers.add(true)
                                            }else triggeredTriggers.add(false)
                                        }
                                        3 -> {
                                            if (relevantWeather.snow == null) {
                                                relevantWeather.snow = 0.00
                                            }
                                            else {
                                                when (trigger.correspondingIntensity){
                                                    0.00f -> trigger.correspondingIntensity = 0.00f
                                                    0.33333334f -> trigger.correspondingIntensity = 7.00f
                                                    0.6666666f -> trigger.correspondingIntensity = 14.00f
                                                    1f -> trigger.correspondingIntensity = 21.00f
                                                }
                                            }
                                            if (relevantWeather.snow!! >= trigger.correspondingIntensity.toDouble()){
                                                triggeredTriggers.add(true)
                                            }else triggeredTriggers.add(false)
                                        }
                                        4 -> {
                                            when(trigger.correspondingIntensity){
                                                0.00f -> trigger.correspondingIntensity = 0.30f
                                                0.2f -> trigger.correspondingIntensity = 1.5f
                                                0.4f -> trigger.correspondingIntensity = 3.3f
                                                0.6f -> trigger.correspondingIntensity = 5.4f
                                                0.8f -> trigger.correspondingIntensity = 7.9f
                                                1f -> trigger.correspondingIntensity = 10.7f
                                            }
                                            if(relevantWeather.speed >= trigger.correspondingIntensity.toDouble()){
                                                triggeredTriggers.add(true)
                                            }else triggeredTriggers.add(false)
                                        }
                                        5 -> {
                                            relevantWeather.temp.max = (((relevantWeather.temp.max - 273.15) * 10f).roundToInt() /10).toDouble()

                                            if (relevantWeather.temp.max >= trigger.correspondingIntensity.toDouble()){
                                                triggeredTriggers.add(true)
                                            }else triggeredTriggers.add(false)
                                        }
                                        else -> {}//this will not be executed ever
                                    }

                                }else{

                                    when(trigger.weatherPhenomenon){
                                        0 -> if (relevantWeather.clouds.toInt() <= 20){
                                            triggeredTriggers.add(true)
                                        }else triggeredTriggers.add(false)

                                        1 -> if (relevantWeather.clouds <= (trigger.correspondingIntensity * 100).toLong()){
                                            triggeredTriggers.add(true)
                                        }else triggeredTriggers.add(false)

                                        2 -> {
                                            if (relevantWeather.rain == null) {
                                                relevantWeather.rain = 0.00
                                            }
                                            else{
                                                when (trigger.correspondingIntensity){
                                                    0.00.toFloat() -> trigger.correspondingIntensity = 0.00f
                                                    0.33333334.toFloat() -> trigger.correspondingIntensity = 7.00f
                                                    0.6666666.toFloat() -> trigger.correspondingIntensity = 14.00f
                                                    1f -> trigger.correspondingIntensity = 21.00f
                                                }
                                            }
                                            if (relevantWeather.rain!! <= trigger.correspondingIntensity.toDouble()){
                                                triggeredTriggers.add(true)
                                            }else triggeredTriggers.add(false)
                                        }
                                        3 -> {
                                            if (relevantWeather.snow == null) {
                                                relevantWeather.snow = 0.00
                                            }
                                            else {
                                                when (trigger.correspondingIntensity){
                                                    0.00f -> trigger.correspondingIntensity = 0.00f
                                                    0.33333334f -> trigger.correspondingIntensity = 7.00f
                                                    0.6666666f -> trigger.correspondingIntensity = 14.00f
                                                    1f -> trigger.correspondingIntensity = 21.00f
                                                }
                                            }
                                            if (relevantWeather.snow!! <= trigger.correspondingIntensity.toDouble()){
                                                triggeredTriggers.add(true)
                                            }else triggeredTriggers.add(false)
                                        }
                                        4 -> {
                                            when(trigger.correspondingIntensity){
                                                0.00f -> trigger.correspondingIntensity = 0.30f
                                                0.2f -> trigger.correspondingIntensity = 1.5f
                                                0.4f -> trigger.correspondingIntensity = 3.3f
                                                0.6f -> trigger.correspondingIntensity = 5.4f
                                                0.8f -> trigger.correspondingIntensity = 7.9f
                                                1f -> trigger.correspondingIntensity = 10.7f
                                            }
                                            if(relevantWeather.speed <= trigger.correspondingIntensity.toDouble()){
                                                triggeredTriggers.add(true)
                                            }else triggeredTriggers.add(false)
                                        }
                                        5 -> {
                                            relevantWeather.temp.max = (((relevantWeather.temp.max - 273.15) * 10f).roundToInt() /10).toDouble()
                                            if (relevantWeather.temp.max <= trigger.correspondingIntensity){
                                                triggeredTriggers.add(true)
                                            }else triggeredTriggers.add(false)
                                        }
                                        else -> {}//this will not be executed ever
                                    }

                                }

                            }

                            if (!triggeredTriggers.contains(false)) {
                                var message = event.title

                                val launchAppIntent = Intent(_context, MainActivity::class.java)
                                val launchPendingIntent = PendingIntent.getActivity(
                                    _context,
                                    1,
                                    launchAppIntent,
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
                                )

                                val notification = NotificationCompat.Builder(_context, channelID)
                                    .setSmallIcon(R.drawable.ic_baseline_anchor_24)
                                    .setContentTitle("Triggered Hook:")
                                    .setContentText(message)
                                    .setContentIntent(launchPendingIntent)
                                    .build()


                                val id: Int = SystemClock.uptimeMillis().toInt()

                                val manager = _context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                manager.notify(id, notification)


                            }
                        }

                    } else {

                        Log.e("shit", forecast.city.name + " ocurred when trying to get data for certain event position")
                    }
                }


            }
        }

    }






}