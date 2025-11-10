plugins {
    `kotlin-dsl`
}

group = "io.github.msaggik.detektsettings"

dependencies {
    implementation(libs.detekt.analysis)
    implementation(libs.detekt.analysis.cli)
    implementation(libs.detekt.analysis.formatting)
    implementation(libs.detekt.analysis.libraries)
}