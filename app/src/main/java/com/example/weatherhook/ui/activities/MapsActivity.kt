package com.example.weatherhook.ui.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherhook.R
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.WeatherHookEventList
import com.example.weatherhook.data.repository.EventRepo
import com.example.weatherhook.databinding.ActivityMapsBinding
import com.example.weatherhook.services.locationService.LocationService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var repo = EventRepo()
    private lateinit var data: WeatherHookEventList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = SQLiteHelper(this)
        data = repo.getAllEvents(db)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val posPair = LocationService().getLocationPair(this)
        val camPos = LatLng(posPair.first.toDouble(),posPair.second.toDouble())

        data.events.forEach { event ->
            mMap.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            event.location.first.toDouble(),
                            event.location.second.toDouble()
                        )
                    )
                    .title(
                        event.title
                    )
            )
        }

        val cameraPosition = CameraPosition.Builder()
            .target(camPos)
            .zoom(9f)
            .tilt(30f)
            .build()
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }
}