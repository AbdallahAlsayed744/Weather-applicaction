package com.example.weatherapp.di

import com.example.domain.repo.CurrentWeatherrepo
import com.example.domain.repo.Forcastdatarepo
import com.example.domain.usecases.CurrentweatherUseCase
import com.example.domain.usecases.ForcastdataUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Usecasemodule {


    @Provides
    @Singleton
    fun provideCurrentweatherUseCase(repo: CurrentWeatherrepo): CurrentweatherUseCase {
        return CurrentweatherUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideForcastDaysUsecase(repo:Forcastdatarepo):ForcastdataUsecase{
        return ForcastdataUsecase(repo)
    }


}