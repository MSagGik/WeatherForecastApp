plugins {
    `kotlin-dsl`
}

group = "io.github.msaggik.detektsettings"

dependencies {
    implementation(libs.detekt.analysis.plugin)
}