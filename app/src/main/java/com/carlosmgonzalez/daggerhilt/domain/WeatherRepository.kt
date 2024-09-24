package com.carlosmgonzalez.daggerhilt.domain

import com.carlosmgonzalez.daggerhilt.model.CoordsResponse
import com.carlosmgonzalez.daggerhilt.model.WeatherResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(
        lat: String,
        lon: String
    ): WeatherResponse

    suspend fun getCoordsByName(
        name: String
    ): List<CoordsResponse>
}