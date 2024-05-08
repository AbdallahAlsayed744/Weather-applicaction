package com.example.data.repo

import android.util.Log
import com.example.data.remote.Api
import com.example.domain.entity.my_weather
import com.example.domain.repo.CurrentWeatherrepo
import retrofit2.Response
import javax.inject.Inject

class CurrentWeatherrepoimple (
    private val api: Api
): CurrentWeatherrepo {


    override suspend fun getcurrentweather(key: String, q: String): Response<my_weather> {
        val response=api.getWeatherData(key,q)
        if (response.isSuccessful&&response.body()!=null){
            Log.d("response","Success")
            Log.d("response",response.body().toString())

        }
        else{
            Log.d("response","Failled")
            Log.d("response",response.body().toString())
        }
        return response
    }


}