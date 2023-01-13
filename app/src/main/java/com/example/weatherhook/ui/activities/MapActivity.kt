package com.example.weatherhook.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherhook.R


class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_screen)

        val action=supportActionBar
        action!!.title = "Map"
    }
}