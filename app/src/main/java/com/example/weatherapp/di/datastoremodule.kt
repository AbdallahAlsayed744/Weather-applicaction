package com.example.weatherapp.di

import android.app.Application
import com.example.data.local.datastore.locamangerimpl
import com.example.data.local.datastore.myappsaveorread
import com.example.data.local.datastore.readentry
import com.example.data.local.datastore.saveentry
import com.example.domain.datastoreinterface.LocalManger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object datastoremodule {

    @Provides
    @Singleton
    fun providelocalmanger(app: Application): LocalManger {
        return locamangerimpl(app)
    }

    @Provides
    @Singleton
    fun providesaveorreaddata(localmanager:LocalManger): myappsaveorread {
        return myappsaveorread(saveentry(localmanager), readentry(localmanager))
    }



}