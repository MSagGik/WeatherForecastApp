import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

private val weatherApiKey = "WEATHER_API_KEY"
private val weatherProperties = "weather.properties"

private val apiKey: String = run {
    val file = rootProject.file(weatherProperties)
    if (file.exists()) {
        Properties().apply { file.inputStream().use(::load) }
            .getProperty(weatherApiKey)
            ?.also { println("$weatherProperties: ${it.take(5)}***") }
            ?: "missing_in_file".also { println("$weatherProperties: $weatherApiKey not found") }
    } else {
        System.getenv(weatherApiKey)
            ?.also { println("env: ${it.take(5)}***") }
            ?: "fallback_debug_key".also { println("fallback!") }
    }
}

android {
    namespace = "io.github.msaggik.databackend"
    compileSdk {
        version = release(libs.versions.compileSdk.get().toInt())
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildTypes.configureEach {
        buildConfigField("String", weatherApiKey, "\"$apiKey\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.koin.android)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.logging.interceptor)
}
