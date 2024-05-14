package com.example.data.remote

import android.content.res.Resources
import com.example.domain.entity.my_Forcast
import com.example.domain.entity.my_weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("current.json")
    suspend fun getWeatherData(
        @Query("key") key:String,
        @Query("q") q:String
    ): Response<my_weather>

    @GET("forecast.json")
    suspend fun getforcastdata(
        @Query("key") key:String,
        @Query("q") q:String
    ): Response<my_Forcast>
}