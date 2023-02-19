package com.example.weatherhook.data.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

//Forecast Data Classes
data class ApiData (
    val city: City,
    val cod: String,
    val message: Double,
    val cnt: Long,
    val list: List<ListElement>
) {

    fun fromJson(json: String): ApiData {
        return Gson().fromJson(json, ApiData::class.java)
    }
}

data class City (
    val id: Long,
    var name: String,
    val coord: Coord,
    val country: String,
    val population: Long,
    val timezone: Long
)

data class Coord (
    val lon: Double,
    val lat: Double
)

data class ListElement (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,

    @SerializedName("feels_like")
    val feelsLike: FeelsLike,

    val pressure: Long,
    val humidity: Long,
    val weather: List<ApiWeather>,
    val speed: Double,
    val deg: Long,
    val gust: Double,
    val clouds: Long,
    val pop: Double,
    var rain: Double? = null,
    var snow: Double? = null
)

data class FeelsLike (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp (
    val day: Double,
    val min: Double,
    var max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class ApiWeather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)








// GEO LOCATION GOOGLE


data class GeoDataApi (
    @SerializedName("plus_code")
    val plusCode: PlusCode,

    val results: List<Result>,
    val status: String
) {

    fun fromJson(json: String): GeoDataApi {
        return Gson().fromJson(json, GeoDataApi::class.java)
    }
}

data class PlusCode (
    @SerializedName("compound_code")
    val compoundCode: String,

    @SerializedName("global_code")
    val globalCode: String
)

data class Result (
    @SerializedName("address_components")
    val addressComponents: List<AddressComponent>,

    @SerializedName("formatted_address")
    val formattedAddress: String,

    val geometry: Geometry,

    @SerializedName("place_id")
    val placeID: String,

    @SerializedName("plus_code")
    val plusCode: PlusCode? = null,

    val types: List<String>,

    @SerializedName("postcode_localities")
    val postcodeLocalities: List<String>? = null
)

data class AddressComponent (
    @SerializedName("long_name")
    val longName: String,

    @SerializedName("short_name")
    val shortName: String,

    val types: List<String>
)

data class Geometry (
    val location: Location,

    @SerializedName("location_type")
    val locationType: String,

    val viewport: Bounds,
    val bounds: Bounds? = null
)

data class Bounds (
    val northeast: Location,
    val southwest: Location
)

data class Location (
    val lat: Double,
    val lng: Double
)