package com.carlosmgonzalez.daggerhilt.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatterTime(timestamp: Long): String {
    val instant = Instant.ofEpochSecond(timestamp)

    val formatter = DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}