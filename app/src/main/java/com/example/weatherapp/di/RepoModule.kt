package com.example.weatherapp.di

import com.example.data.remote.Api
import com.example.data.repo.CurrentWeatherrepoimple
import com.example.data.repo.Forcastdatarepoimpl
import com.example.domain.repo.CurrentWeatherrepo
import com.example.domain.repo.Forcastdatarepo
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

    @Provides
    @Singleton
    fun provideForcastdaysrepo(api:Api): Forcastdatarepo {
        return Forcastdatarepoimpl(api)
    }
}