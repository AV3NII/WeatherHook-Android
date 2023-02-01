package com.example.weatherhook.data.api

import com.example.weatherhook.data.models.ApiData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "dd0b74cc043811f15991744dba08d306"
const val exclude = "hourly"

// https://api.openweathermap.org/data/2.5/forecast/daily?lat=33.44&lon=-94.04&exclude=hourly&appid=dd0b74cc043811f15991744dba08d306
// api.openweathermap.org/data/2.5/forecast/daily?lat={lat}&lon={lon}&cnt=7&appid={API key}

interface OpenWeatherApi {
    @GET("daily")
    suspend fun getWeatherForcast(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("cnt") daysInFuture: Int,
    ): Deferred<ApiData>

    companion object{
        operator fun invoke(): OpenWeatherApi{
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter( "appid", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val requestInterceptor2 = Interceptor{ chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter( "exclude", exclude)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okhttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(requestInterceptor2)
                .build()

            val res = Retrofit.Builder()
                .client(okhttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/forecast/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApi::class.java)


            return res
        }
    }


}