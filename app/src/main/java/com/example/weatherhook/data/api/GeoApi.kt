package com.example.weatherhook.data.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherhook.BuildConfig
import com.example.weatherhook.data.models.*

class GeoApi {
    val apiKey = BuildConfig.MAPS_API_KEY

    fun callApi(lat: Float, lon: Float, context: Context, callback: (GeoDataApi) -> Unit) {


        // https://maps.googleapis.com/maps/api/geocode/json?latlng=52.54054,13.005444&key=
        val baseUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
        val url = baseUrl + "${lat},${lon}&key=${apiKey}"


        val queue = Volley.newRequestQueue(context)

        var locationName = GeoDataApi(
            plusCode = PlusCode("GCC3+2X4 Berlin, Germany", "9F4MGCC3+2X4"),
            results = listOf(Result(
                addressComponents = listOf(AddressComponent("Spandauer Straße", "Spandauer Str.", listOf("route"))),
                formattedAddress = "Spandauer Str., 10178 Berlin, Germany",
                geometry = Geometry(
                    location = Location(52.5200951, 13.4048887),
                    locationType = "GEOMETRIC_CENTER",
                    viewport = Bounds(Location(37.1, -122.1), Location(37.2, -122.2)),
                    bounds = Bounds(Location(37.05, -122.05), Location(37.25, -122.25))
                ),
                placeID = "GhIJRdREn49CSkARzsR0IVbPKkA",
                plusCode = PlusCode("GCC3+2X Berlin, Germany", "9F4MGCC3+2X"),
                types = listOf("street_address"),
                postcodeLocalities = listOf("Anytown")
            )),
            status = "OK"
        )


        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->

                try {

                    locationName = locationName.fromJson(response)
                    callback(locationName)
                } catch (e: Exception) {

                    // handle the exception and take appropriate action
                    locationName = GeoDataApi(
                        plusCode = PlusCode("GSON ERROR", "9F4MGCC3+2X4"),
                        results = listOf(Result(
                            addressComponents = listOf(AddressComponent("Spandauer Straße", "Spandauer Str.", listOf("route"))),
                            formattedAddress = "Spandauer Str., 10178 Berlin, Germany",
                            geometry = Geometry(
                                location = Location(52.5200951, 13.4048887),
                                locationType = "GEOMETRIC_CENTER",
                                viewport = Bounds(Location(37.1, -122.1), Location(37.2, -122.2)),
                                bounds = Bounds(Location(37.05, -122.05), Location(37.25, -122.25))
                            ),
                            placeID = "GhIJRdREn49CSkARzsR0IVbPKkA",
                            plusCode = PlusCode("GCC3+2X Berlin, Germany", "9F4MGCC3+2X"),
                            types = listOf("street_address"),
                            postcodeLocalities = listOf("Anytown")
                        )),
                        status = "OK"
                    )

                    callback(locationName)
                }
            },
            {


            }
        )


        queue.add(stringRequest)
    }
}