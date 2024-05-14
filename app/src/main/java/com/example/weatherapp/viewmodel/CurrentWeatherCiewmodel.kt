package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Current
import com.example.domain.entity.Forecastday
import com.example.domain.entity.Location
import com.example.domain.usecases.CurrentweatherUseCase
import com.example.domain.usecases.ForcastdataUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherCiewmodel @Inject constructor(
    private val useCase: CurrentweatherUseCase,
    private val forcastuseCase: ForcastdataUsecase
) :ViewModel() {

    private val _currentweatherlocation:MutableStateFlow<Location?> = MutableStateFlow(null)
    val currentweatherlocation:StateFlow<Location?> get() = _currentweatherlocation

    private val _currentweathercurrent:MutableStateFlow<Current?> = MutableStateFlow(null)
    val currentweathercurrent:StateFlow<Current?> get() = _currentweathercurrent

    private val _Forcastweathercurrent:MutableStateFlow<Forecastday?> = MutableStateFlow(null)
    val Forcastweathercurrent:StateFlow<Forecastday?> get() = _Forcastweathercurrent


//    init {
//        getcurrentweather("05dbc44ab35d4509b46104404240605","26.5686661,31.6884415")
//    }

    fun getForcastweather(key:String,q:String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = forcastuseCase.invoke(key, q)
            if (response.isSuccessful && response.body() != null) {
                _Forcastweathercurrent.value = response.body()!!.forecast.forecastday[0]
            }

        } catch (e: Exception) {
            Log.d("error here", e.message.toString())
        }

    }

    fun getcurrentweather(key:String,q:String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = useCase.invoke(key,q)
            if (response.isSuccessful&&response.body()!=null){
                _currentweatherlocation.value = response.body()!!.location
                _currentweathercurrent.value = response.body()!!.current
            }

        }catch (e:Exception){
            Log.d("error here",e.message.toString())
        }


    }
}