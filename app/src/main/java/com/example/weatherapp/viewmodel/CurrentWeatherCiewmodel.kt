package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Current
import com.example.domain.entity.Day
import com.example.domain.entity.Forecastday
import com.example.domain.entity.Location
import com.example.domain.usecases.CurrentweatherUseCase
import com.example.domain.usecases.ForcastdataUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _itWillRain: MutableStateFlow<Day?> = MutableStateFlow(null)
    val itWillRain: StateFlow<Day?> get() = _itWillRain


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


//    init {
//        getcurrentweather("05dbc44ab35d4509b46104404240605","26.5686661,31.6884415")
//    }

    fun itwillrainornot(key: String,q:String) = viewModelScope.launch(Dispatchers.IO) {
       val resopnse =forcastuseCase(key, q)
        _itWillRain.value = resopnse.body()!!.forecast.forecastday[0].day
    }

    fun getForcastweather(key:String,q:String) = viewModelScope.launch(Dispatchers.IO) {
        try {
           _loading.value=true
            val response = forcastuseCase.invoke(key, q)
            if (response.isSuccessful && response.body() != null) {
                _Forcastweathercurrent.value = response.body()!!.forecast.forecastday[0]
            }

        } catch (e: Exception) {
            Log.d("error here", e.message.toString())
        }finally {
            _loading.value=false
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

    fun clearWeatherData() {
        _currentweatherlocation.value=null
        _currentweathercurrent.value=null
    }
}