package com.example.weatherhook.ui.activities

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherhook.databinding.ActivityMainBinding
import com.example.weatherhook.services.notificationService.*
import com.google.android.gms.location.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        //viewModel.inputs.onTurnOnNotificationsClicked(granted)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val action=supportActionBar
        action!!.title = "Weather Hook Home"




        //Log.d("APITest", callApi(this).city.toString())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        pushNotificationPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            createNotificationChannel()}

        scheduleNotification("ITS GETTING COLD", "seems like you cant go swimming ;(")
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


    private fun scheduleNotification(title: String, message: String) {
        val intent = Intent(applicationContext, NotificationBroadcast::class.java)
        //val title = "Test Notification"
        //val message = "Test Message"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

        //showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

    private fun getTime() : Long {
        val calendar = Calendar.getInstance() // gets a calendar using the default time zone and locale.

        calendar.add(Calendar.SECOND, 10)
        return  calendar.timeInMillis
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
