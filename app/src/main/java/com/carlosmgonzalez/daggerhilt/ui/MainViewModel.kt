package com.carlosmgonzalez.daggerhilt.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosmgonzalez.daggerhilt.domain.WeatherRepository
import com.carlosmgonzalez.daggerhilt.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {
    private val _weatherUiState = MutableStateFlow(WeatherUiState())
    val weatherUiState = _weatherUiState.asStateFlow()

    init {
        fetchCurrentWeather()
    }

    private fun fetchCurrentWeather() {
        viewModelScope.launch {
            val currentWeather = weatherRepository
                .getCurrentWeather(lat = "4.43917", lon = "-75.2113267")

            _weatherUiState.update { state ->
                state.copy(
                    currentWeather = currentWeather
                )
            }
        }
    }

    fun searchCurrentWeather(name: String) {
        viewModelScope.launch {
            try {
                val coords = weatherRepository.getCoordsByName(name)
                val currentWeather = weatherRepository.getCurrentWeather(
                    lat = coords[0].lat.toString(),
                    lon = coords[0].lon.toString()
                )
                _weatherUiState.update { state ->
                    state.copy(
                        currentWeather = currentWeather
                    )
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error Search Current Weather", e)
            }
        }
    }

//    suspend fun getCoordsByName(name: String): CoordsResponse  {
//        return weatherRepository.getCoordsByName(name = name)
//    }
}

data class WeatherUiState(
    var currentWeather: WeatherResponse? = null
)