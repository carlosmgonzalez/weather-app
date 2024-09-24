package com.carlosmgonzalez.daggerhilt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val sys: Sys,
    val name: String,
    val timezone: Int
)

@Serializable
data class Coord(
    val lon: Double,
    val lat: Double
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Double,
    val humidity: Int,
    @SerialName("sea_level")
    val seaLevel: Double,
    @SerialName("grnd_level")
    val grndLevel: Double,
)

@Serializable
data class Sys(
    val country: String,
    val sunrise: Int,
    val sunset: Int
)

@Serializable
data class CoordsResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String
)