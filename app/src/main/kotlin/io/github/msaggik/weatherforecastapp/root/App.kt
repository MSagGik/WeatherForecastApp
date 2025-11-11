package io.github.msaggik.weatherforecastapp.root

import io.github.msaggik.databackend.di.backendDataModule
import io.github.msaggik.weatherforecastapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                backendDataModule
            )
        }
    }
}
