import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    id("io.gitlab.arturbosch.detekt")
}

private val detektConfigPath = "${rootDir}/devops/detekt-settings/configurations/detekt-tests.yml"

// ✅ Главная задача для всех модулей
val detektAll by tasks.register<Detekt>("detektAll") {
    description = "Run Detekt across the entire codebase."
    parallel = true
    autoCorrect = false
    jvmTarget = JavaVersion.VERSION_17.toString()

    buildUponDefaultConfig = true

    setSource(files(projectDir))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")

    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
    }

    config.setFrom(files(project.rootDir.resolve(detektConfigPath)))
}

// ✅ Форматирование
val detektFormat by tasks.register<Detekt>("detektFormat") {
    description = "Automatically formats Kotlin code using Detekt rules."
    parallel = true
    autoCorrect = true
    jvmTarget = JavaVersion.VERSION_17.toString()

    config.setFrom(files(project.rootDir.resolve(detektConfigPath)))
}

// ✅ Создание baseline
val detektProjectBaseline by tasks.register<DetektCreateBaselineTask>("detektProjectBaseline") {
    description = "Regenerates the Detekt baseline file."
    setSource(files(projectDir))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")

    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)
    jvmTarget = JavaVersion.VERSION_17.toString()

    config.setFrom(files(project.rootDir.resolve(detektConfigPath)))
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("detekt", libs.findLibrary("detekt.analysis.cli").get())
    add("detektPlugins", libs.findLibrary("detekt.analysis.formatting").get())
    add("detektPlugins", libs.findLibrary("detekt.analysis.libraries").get())
}