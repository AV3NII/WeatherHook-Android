package com.example.weatherhook.data.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.weatherhook.R

class IconMapper {
    @Composable
    fun mapApiIconToProjectIcon(icon:String):Painter{
        val appIcons = arrayOf(
            painterResource(R.drawable.baseline_wb_sunny_24),
            painterResource(R.drawable.baseline_cloud_24),
            painterResource(R.drawable.baseline_grain_24),
            painterResource(R.drawable.baseline_snowboarding_24),
            painterResource(R.drawable.baseline_mist_fog_24),
            painterResource(R.drawable.baseline_error_outline_24)
        )

        return when (icon.dropLast(1)){
            "01"-> appIcons[0]
            "02"-> appIcons[0]
            "03"-> appIcons[1]
            "04"-> appIcons[1]
            "09"-> appIcons[2]
            "10"-> appIcons[2]
            "11"-> appIcons[2]
            "13"-> appIcons[3]
            "50"-> appIcons[4]
            else -> appIcons[5]
        }
    }
}