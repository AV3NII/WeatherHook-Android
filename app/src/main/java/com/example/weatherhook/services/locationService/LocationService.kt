
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
                "Unknown Location"
            }
        } ?: "Unknown Location"
        return locationName
    }

    fun getLocationPair(context: Context): Pair<Float, Float>? {
        val location = getLocationLongLat(context)

        if (location != null) {
            var locationPair = Pair<Float, Float>(location.longitude.toFloat(), location.latitude.toFloat())
            return locationPair
        }
        else {
            var locationPair = Pair<Float, Float>(-1f, -2f)
            return locationPair
        }

    }
}