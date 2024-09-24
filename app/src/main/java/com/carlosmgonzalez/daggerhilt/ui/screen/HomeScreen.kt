package com.carlosmgonzalez.daggerhilt.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.carlosmgonzalez.daggerhilt.R
import com.carlosmgonzalez.daggerhilt.model.WeatherResponse
import com.carlosmgonzalez.daggerhilt.ui.MainViewModel
import com.carlosmgonzalez.daggerhilt.utils.formatterTime
import com.carlosmgonzalez.daggerhilt.utils.kelvinToCelsius

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {
    var isSearching by rememberSaveable { mutableStateOf(false) }
    var search by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Weather") },
                actions = {
                    Row {
                        AnimatedVisibility(isSearching) {
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                TextField(
                                    value = search,
                                    onValueChange = { search = it},
                                    maxLines = 1,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.55f)
                                        .fillMaxHeight(0.80f),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Search
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            mainViewModel.searchCurrentWeather(search)
                                        }
                                    ),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                                    )
                                )
                            }
                        }
                        IconButton(
                            onClick = {isSearching = !isSearching}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                )
            )
        }
    ) { innerPadding ->
        val padding = innerPadding
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            contentColor = contentColorFor(MaterialTheme.colorScheme.background)
        ) {
            val weatherUiState by mainViewModel.weatherUiState.collectAsState()
            val currentWeather = weatherUiState.currentWeather

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(R.drawable.bg_weather_app_day),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alpha = 0.6f,
                    modifier = Modifier.fillMaxSize()
                )
                if (currentWeather != null) {
                    WeatherInformation(currentWeather)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(bottom = innerPadding.calculateBottomPadding() + 20.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        CardInfo(
                            icon = Icons.Default.WbSunny,
                            info = formatterTime(currentWeather.sys.sunrise.toLong())
                        )
                        CardInfo(
                            icon = Icons.Default.Nightlight,
                            info = formatterTime(currentWeather.sys.sunset.toLong())
                        )
                        CardInfo(
                            icon = Icons.Default.WaterDrop,
                            info = "${currentWeather.main.humidity}%"
                        )
                        CardInfo(
                            icon = Icons.Default.Thermostat,
                            info = "${currentWeather.main.tempMax.kelvinToCelsius()}/${currentWeather.main.tempMin.kelvinToCelsius()}"
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherInformation(
    currentWeather: WeatherResponse
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://openweathermap.org/img/wn/${currentWeather.weather[0].icon}@2x.png")
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(130.dp)
            )
            Row{
                Text(
                    text = "${currentWeather.main.temp.kelvinToCelsius()}",
                    fontWeight = FontWeight.W500,
                    fontSize = 100.sp
                )
                Text(text = "°C", style = MaterialTheme.typography.headlineLarge)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(imageVector = Icons.Filled.Place, contentDescription = "location")
            Text(
                text = "${currentWeather.name} - ${currentWeather.sys.country}",
                fontSize = 24.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardInfo(
    icon: ImageVector,
    info: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        ),
        modifier = Modifier.size(width = 60.dp, height = 80.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Text(
                text = info
            )
        }
    }
}


//Card(
//colors = CardDefaults.cardColors(
//containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
//)
//) {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(6.dp)
//    ) {
//        Icon(
//            imageVector = Icons.Default.WbSunny,
//            contentDescription = "Sunny"
//        )
//        Text(
//            text = formatterTime(currentWeather.sys.sunrise.toLong())
//        )
//    }
//}