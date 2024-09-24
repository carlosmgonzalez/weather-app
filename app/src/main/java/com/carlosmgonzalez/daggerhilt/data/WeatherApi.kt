package com.carlosmgonzalez.daggerhilt.data

import com.carlosmgonzalez.daggerhilt.model.CoordsResponse
import com.carlosmgonzalez.daggerhilt.model.WeatherResponse
import com.carlosmgonzalez.daggerhilt.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("appid") apiKey: String = API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): WeatherResponse

    @GET("geo/1.0/direct")
    suspend fun getCoordsByName(
        @Query("appid") apiKey: String = API_KEY,
        @Query("limit") limit: String = "3",
        @Query("q") name: String
    ): List<CoordsResponse>
}