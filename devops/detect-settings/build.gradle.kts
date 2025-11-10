plugins {
    `kotlin-dsl`
}

group = "io.github.msaggik.detectsettings"

dependencies {
    implementation(libs.detekt.analisis)
    implementation(libs.detekt.analisis.cli)
    implementation(libs.detekt.analisis.formatting)
    implementation(libs.detekt.analisis.libraries)
}