package io.github.msaggik.weatherforecastapp

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.msaggik.weatherforecastapp.presentation.viewmodel.WeatherViewModel
import io.github.msaggik.weatherforecastapp.presentation.viewmodel.state.ForecastState
import io.github.msaggik.weatherforecastapp.ui.theme.WeatherForecastAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue
import android.os.Bundle
import android.util.Log

class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherForecastAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // example
        weatherViewModel.getWeather(
            location = "55.7569,37.6151",
            days = 3
        )

        weatherViewModel.forecastStateLiveData.observe(this) { state: ForecastState ->

            if (state is ForecastState.Content) {
                Log.e("", state.forecast.toString())
            } else {
                Log.e("", state.toString())
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherForecastAppTheme {
        Greeting("Android")
    }
}
