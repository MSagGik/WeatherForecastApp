package io.github.msaggik.weatherforecastapp.presentation.ui.main

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.entry.entryModelOf
import io.github.msaggik.weatherforecastapp.domain.models.entities.ForecastDay
import io.github.msaggik.weatherforecastapp.utils.formatDate

@Composable
fun TemperatureChart(
    forecast: List<ForecastDay>,
    isVisible: Boolean = true,
    animationDuration: Int = 400
) {
    @Suppress("SpreadOperator")
    val chartModel = entryModelOf(
        *forecast.mapIndexed { i, day -> i.toFloat() to day.avgTempC }.toTypedArray()
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing),
        label = "TemperatureChartAlpha"
    )

    val chartColors = ChartStyle.fromColors(
        entityColors = listOf(MaterialTheme.colorScheme.primary),
        axisLabelColor = MaterialTheme.colorScheme.onBackground,
        axisLineColor = MaterialTheme.colorScheme.outlineVariant,
        axisGuidelineColor = Color.Transparent,
        elevationOverlayColor = MaterialTheme.colorScheme.secondary
    )

    ProvideChartStyle(chartColors) {
        val style = currentChartStyle

        Chart(
            chart = lineChart(lines = listOf(style.lineChart.lines[0])),
            model = chartModel,
            startAxis = rememberStartAxis(
                title = "°C",
                titleComponent = TextComponent.Builder().apply {
                    color = MaterialTheme.colorScheme.onBackground.toArgb()
                    textSizeSp = 14.sp.value
                }.build(),
                valueFormatter = { value, _ -> "%.0f".format(value) }
            ),
            bottomAxis = rememberBottomAxis(
                title = "День",
                titleComponent = TextComponent.Builder().apply {
                    color = MaterialTheme.colorScheme.onBackground.toArgb()
                    textSizeSp = 14.sp.value
                }.build(),
                valueFormatter = { value, _ ->
                    val index = value.toInt()
                    if (index in forecast.indices) {
                        formatDate(forecast[index].date)
                    } else {
                        ""
                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .graphicsLayer { this.alpha = alpha }
        )
    }
}
