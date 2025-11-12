package io.github.msaggik.weatherforecastapp.presentation.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.msaggik.weatherforecastapp.presentation.ui.main.MainScreen
import io.github.msaggik.weatherforecastapp.presentation.ui.theme.WeatherForecastAppTheme
import io.github.msaggik.weatherforecastapp.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.os.Bundle

class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeatherForecastAppTheme {
                val navController = rememberNavController()
                var currentCity by remember { mutableStateOf("Moscow") }

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(
                            viewModel = weatherViewModel,
                            currentCity = currentCity,
                            onSettingsClick = { navController.navigate("settings") }
                        )
                    }
                    composable("settings") {
//                        SettingsScreen(
//                            onCitySelected = { city ->
//                                currentCity = city
//                                weatherViewModel.loadWeather(city, 3)
//                                navController.popBackStack()
//                            },
//                            onBack = { navController.popBackStack() }
//                        )
                    }
                }
            }
        }
    }
}
