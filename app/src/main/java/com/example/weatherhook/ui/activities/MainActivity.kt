package com.example.weatherhook.ui.activities

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherhook.R
import com.example.weatherhook.data.api.Api
import com.example.weatherhook.data.api.GeoApi
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ForecastData
import com.example.weatherhook.data.models.ForecastDay
import com.example.weatherhook.data.repository.ForecastRepo
import com.example.weatherhook.databinding.ActivityMainBinding
import com.example.weatherhook.services.locationService.LocationService
import com.example.weatherhook.services.notificationService.Notification
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PERMISSIONS_REQUEST_CODE = 123

    private val forecastRepo = ForecastRepo()
    val db = SQLiteHelper(this)


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (checkPermissions()) {
                recreate()
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val notificationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        return locationPermission == PackageManager.PERMISSION_GRANTED && notificationPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the shared preferences file
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


        val language: String? = sharedPreferences.getString("language", "en")
        val locale = Locale(language!!)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)


        if (!checkPermissions()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                PERMISSIONS_REQUEST_CODE
            )
        }






        val action=supportActionBar
        action!!.title = getString(R.string.app_name)

        createNotificationChannel()

        val location = LocationService().getLocationPair(this)
        var locationName = "Error"
        val apiLocationName = forecastRepo.getName(db)





        GeoApi().callApi(location.first, location.second, this) { locationNameApi ->
            if (locationNameApi.status == "OK") {
                locationName = locationNameApi.results[0].addressComponents[2].longName
                } else {
                locationName = "Berlin"
            }
            // END GeoApi Call -> received Data -> Start of API Call
            if (forecastRepo.getForecast(db) == ForecastData(listOf<ForecastDay>().toMutableList())){
                Api().callApi(location.first,location.second,7, this) { forecast ->
                    if (forecast.cod == "200") {
                        forecastRepo.addForecast(forecast, locationName, this, SQLiteHelper(this))
                        binding = ActivityMainBinding.inflate(layoutInflater)
                        setContentView(binding.root)
                    } else {
                        val test = forecast.city.name
                        Log.e("error", test)
                        binding = ActivityMainBinding.inflate(layoutInflater)
                        setContentView(binding.root)
                    }
                }

            }else{
                if(locationName == apiLocationName) {
                    binding = ActivityMainBinding.inflate(layoutInflater)
                    setContentView(binding.root)
                }
                else {
                    Api().callApi(location.first, location.second,7, this) { forecast ->
                        if (forecast.cod == "200") {
                            forecastRepo.updateForecast(forecast, locationName,this, SQLiteHelper(this))
                            binding = ActivityMainBinding.inflate(layoutInflater)
                            setContentView(binding.root)
                        } else {
                            val test = forecast.city.name
                            Log.e("error", test)
                            binding = ActivityMainBinding.inflate(layoutInflater)
                            setContentView(binding.root)
                        }
                    }
                }

            }
        }



    }



    fun openMap(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)

    }

    fun newHook(view: View) {
        val intent = Intent(this, HookActivity::class.java)
        intent.putExtra("currentEvent", -2)
        startActivity(intent)

    }

    fun openSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)

    }


    private fun createNotificationChannel() {
        val name = "AlertNotifier"
        val desc = "Send Alert Notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(Notification(this).companion.channelID, name, importance)
        channel.description = desc

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        Notification(this).scheduleNotificationManager()

    }

}