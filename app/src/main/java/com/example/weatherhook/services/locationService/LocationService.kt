package com.example.weatherhook.services.locationService
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import java.util.*

class LocationService {
    fun getLocationLongLat(context: Context): Location? {
        fun checkLocationPermission(): Boolean {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                return false
            }
            return true
        }


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

    fun getLocationName (context: Context): String {
        val location = getLocationLongLat(context)

        val locationName = location?.let {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            if (addresses!!.isNotEmpty()) {
                addresses[0].locality
            } else {
                "Berlin"
            }
        } ?: "Berlin"
        return locationName
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