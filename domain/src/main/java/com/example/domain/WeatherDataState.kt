package com.example.domain

import com.example.domain.entity.Forecastday
import com.example.domain.entity.my_Forcast

sealed class DataState<T>(
    val data: T? = null, val exception: Exception? = null
) {

     class Loading<T>(isLoading: T? = null) : DataState<T>()

     class Success<T>(data: T) : DataState<T>(data)

    class Error<T>(message: Exception, data: T? = null) : DataState<T>(data, message)

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Loading -> "Loading[data=$data]"
            is Error -> "Error[exception=$exception, data=$data]"
        }
    }
}
