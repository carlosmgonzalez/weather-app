package com.carlosmgonzalez.daggerhilt.data

import com.carlosmgonzalez.daggerhilt.domain.WeatherRepository
import com.carlosmgonzalez.daggerhilt.model.CoordsResponse
import com.carlosmgonzalez.daggerhilt.model.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository {
    override suspend fun getCurrentWeather(
        lat: String,
        lon: String
    ): WeatherResponse {
        return weatherApi.getCurrentWeather(
            lat = lat,
            lon = lon
        )
    }

    override suspend fun getCoordsByName(
        name: String
    ): List<CoordsResponse> {
        return weatherApi.getCoordsByName(name = name)
    }
}