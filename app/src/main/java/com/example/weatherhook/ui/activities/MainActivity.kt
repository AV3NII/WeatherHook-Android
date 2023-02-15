package com.example.weatherhook.ui.activities

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherhook.databinding.ActivityMainBinding
import com.example.weatherhook.services.notificationService.*
import com.google.android.gms.location.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PERMISSIONS_REQUEST_CODE = 123



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (checkPermissions()) {
                // Permissions granted
            } else {
                // Permissions denied
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val noticationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        return locationPermission == PackageManager.PERMISSION_GRANTED && noticationPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



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


        //setContentView(R.layout.activity_main)
        val action=supportActionBar
        action!!.title = "Weather Hook Home"


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) { createNotificationChannel() }


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


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "AlertNotifier"
        val desc = "Send Alert Notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
