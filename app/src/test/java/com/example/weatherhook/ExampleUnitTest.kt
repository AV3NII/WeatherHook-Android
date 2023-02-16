package com.example.weatherhook

import com.example.weatherhook.data.api.OpenWeatherApi
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class ApiTest(){
    private val _api = OpenWeatherApi()
    private val scope =   GlobalScope
    @Test
    fun apiPing(){

            scope.async {
                try {
                    val res = _api.getWeatherForcast(48.21f, 12.12f, 7).await()
                    print(res)
                    assert(res.toString() != " ")
                }catch (e: Exception){
                    e.stackTrace
                    Assert.fail()
                }

            }



    }

    @Test
    fun testGetWeatherForcast() = runBlocking {
        val openWeatherApi = OpenWeatherApi()
        val apiData = openWeatherApi.getWeatherForcast(33.44f, -94.04f, 7)
        assertNotNull(apiData)
    }



}