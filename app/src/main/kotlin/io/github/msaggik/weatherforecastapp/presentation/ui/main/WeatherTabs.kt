package io.github.msaggik.weatherforecastapp.presentation.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.msaggik.weatherforecastapp.domain.models.entities.ForecastDay

@Composable
fun WeatherTabs(forecast: List<ForecastDay>) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("График", "Список")

    Column {
        PrimaryTabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (tabIndex) {
            0 -> TemperatureChart(forecast)
            1 -> ForecastList(forecast)
        }
    }
}
