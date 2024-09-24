package com.carlosmgonzalez.daggerhilt.di

import com.carlosmgonzalez.daggerhilt.data.WeatherRepositoryImpl
import com.carlosmgonzalez.daggerhilt.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepositoryWeather(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}