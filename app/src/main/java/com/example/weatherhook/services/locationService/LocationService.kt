package com.example.weatherhook.services.locationService

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import java.util.*


class LocationService {
    @SuppressLint("MissingPermission")
    fun getLocationLongLat(context: Context): Location? {

        val locationManager = context.getSystemService(LocationManager::class.java)

        var location: Location?

        try {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        }
        catch (e: java.lang.Exception) {
            location = null
        }


        return location
    }



    fun getName(context: Context, latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val adresses: List<Address>?

        try {
            adresses = geocoder.getFromLocation(latitude, longitude, 1)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        if(adresses != null && adresses.isNotEmpty()) {
            return adresses[0].locality
        }

        return null
    }

    fun getLocationName(context: Context, latitude: Double, longitude: Double): String? {
        var name = getName(context, latitude, longitude)
        for (i in 1..10) {
            name = getName(context, latitude, longitude)
            if (name == null) {
                continue
            }
            else break
        }
        return name
    }





    fun getLocationPair(context: Context): Pair<Float, Float> {
        val location = getLocationLongLat(context)

        return if (location != null) {
            Pair(location.latitude.toFloat(), location.longitude.toFloat())
        } else {
            Pair(52.520008f, 13.404954f)
        }

    }
}