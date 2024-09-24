package com.carlosmgonzalez.daggerhilt.utils

fun Double.kelvinToCelsius(): Int {
    return (this - 273.15).toInt()
}