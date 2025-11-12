package io.github.msaggik.weatherforecastapp.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import io.github.msaggik.weatherforecastapp.domain.models.entities.ForecastDay
import io.github.msaggik.weatherforecastapp.domain.models.entities.Location
import io.github.msaggik.weatherforecastapp.presentation.viewmodel.WeatherViewModel
import io.github.msaggik.weatherforecastapp.presentation.viewmodel.state.ForecastState

private const val DEFAULT_NUMBER_DAY_WEATHER = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    viewModel: WeatherViewModel,
    currentCity: String,
    onSettingsClick: () -> Unit
) {
    val state by viewModel.forecastState.collectAsStateWithLifecycle(ForecastState.Loading)
    val refreshState = rememberPullToRefreshState()
    val isRefreshing = refreshState.distanceFraction > 100f

    if (isRefreshing) {
        LaunchedEffect(Unit) {
            viewModel.loadWeather(currentCity, DEFAULT_NUMBER_DAY_WEATHER)
        }
    }
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.loadWeather(currentCity, DEFAULT_NUMBER_DAY_WEATHER) },
        state = refreshState
    ) {
        Scaffold(
            topBar = {
                WeatherTopBar(currentCity, onSettingsClick)
            }
        ) { padding ->
            WeatherContent(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                state = state
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeatherTopBar(currentCity: String, onSettingsClick: () -> Unit) {
    TopAppBar(
        title = { Text("Погода") },
        actions = {
            Text(text = currentCity, modifier = Modifier.padding(end = 8.dp))
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Настройки"
                )
            }
        }
    )
}

@Composable
private fun WeatherContent(
    modifier: Modifier = Modifier,
    state: ForecastState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        when (state) {
            is ForecastState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is ForecastState.InternetOff -> Text(
                "Нет интернета",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
            is ForecastState.InternetError -> Text(
                "Ошибка",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
            is ForecastState.Content -> {
                val forecast = state.forecast

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    CurrentWeather(day = forecast.forecastDayList[0], location = forecast.location)
                    Spacer(Modifier.height(32.dp))
                    WeatherTabs(forecast = forecast.forecastDayList)
                }
            }
        }
    }
}

@Composable
fun CurrentWeather(day: ForecastDay, location: Location) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("${day.avgTempC}°C", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        AsyncImage(
            model = "https:${day.conditionIcon}",
            contentDescription = day.conditionText,
            modifier = Modifier.size(64.dp)
        )
        Text("Город: ${location.name}, ${location.country}")
        Text("Время: ${location.localTime}")
    }
}
