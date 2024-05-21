package com.example.domain.usecases

import com.example.domain.repo.CurrentWeatherrepo

class CurrentweatherUseCase (
    private val repository: CurrentWeatherrepo
){

     suspend operator fun invoke(key:String,q:String) = repository.getcurrentweather(key,q)




}