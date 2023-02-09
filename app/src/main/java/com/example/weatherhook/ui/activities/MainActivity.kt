package com.example.weatherhook.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherhook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val action=supportActionBar
        action!!.title = "Weather Hook Home"




        //Log.d("APITest", callApi(this).city.toString())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    fun openMap(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)

    }

    fun newHook(view: View) {
        val intent = Intent(this, NewHookActivity::class.java)
        startActivity(intent)

    }

    fun openSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)


    }
}
