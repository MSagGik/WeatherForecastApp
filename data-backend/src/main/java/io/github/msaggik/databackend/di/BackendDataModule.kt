package io.github.msaggik.databackend.di

import io.github.msaggik.databackend.api.WeatherApiClient
import io.github.msaggik.databackend.api.backend.RetrofitClient
import io.github.msaggik.databackend.api.backend.WeatherApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URI_BACKEND = "https://api.weatherapi.com/v1/"

val backendDataModule = module {

    single<WeatherApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URI_BACKEND)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    single<WeatherApiClient> {
        RetrofitClient(
            weatherApiService = get(),
            context = androidContext()
        )
    }
}
