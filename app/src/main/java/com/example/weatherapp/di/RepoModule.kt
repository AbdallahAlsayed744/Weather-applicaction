package com.example.weatherapp.di

import com.example.data.remote.Api
import com.example.data.repo.CurrentWeatherrepoimple
import com.example.domain.repo.CurrentWeatherrepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {


    @Provides
    @Singleton
    fun provideCurrentWeatherrepo(api:Api): CurrentWeatherrepo {
        return CurrentWeatherrepoimple(api)
    }
}