package com.example.weatherhook.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherhook.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val action=supportActionBar
        action!!.title = getString(R.string.settings)
    }


}