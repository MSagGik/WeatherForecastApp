package io.github.msaggik.weatherforecastapp.di

import io.github.msaggik.weatherforecastapp.data.mappers.WeatherMapper
import io.github.msaggik.weatherforecastapp.data.repositoryimpl.backend.WeatherBackendRepositoryImpl
import io.github.msaggik.weatherforecastapp.domain.repository.WeatherBackendRepository
import io.github.msaggik.weatherforecastapp.domain.usecase.WeatherBackendInteractor
import io.github.msaggik.weatherforecastapp.domain.usecase.impl.WeatherBackendInteractorImpl
import io.github.msaggik.weatherforecastapp.presentation.viewmodel.WeatherViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::WeatherViewModel)

    single<WeatherBackendInteractor> {
        WeatherBackendInteractorImpl(
            weatherBackendRepository = get()
        )
    }

    single<WeatherBackendRepository> {
        WeatherBackendRepositoryImpl(
            weatherApiClient = get(),
            weatherMapper = get()
        )
    }

    factory {
        WeatherMapper()
    }
}
