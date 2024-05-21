package com.example.domain.repo

import com.example.domain.entity.my_weather
import retrofit2.Response

interface CurrentWeatherrepo {

    suspend fun getcurrentweather(
        key:String,
        q:String
    ):Response<my_weather>

}