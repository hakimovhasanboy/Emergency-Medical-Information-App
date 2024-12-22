plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

configurations {
    create("ktlint")
    create("detekt")
}

dependencies {
    "ktlint"("com.pinterest.ktlint:ktlint-cli:1.5.0")
    "detekt"("io.gitlab.arturbosch.detekt:detekt-cli:1.22.0-RC3")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

tasks.register("ktlintCheck", JavaExec::class) {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = configurations["ktlint"]
    mainClass.set("com.pinterest.ktlint.Main")
    args("src/**/*.kt", "**.kts", "!**/build/**")
}

tasks.named("check") {
    dependsOn(tasks.named("ktlintCheck"))
}

tasks.register("ktlintFormat", JavaExec::class) {
    group = "formatting"
    description = "Fix Kotlin code style deviations."
    classpath = configurations["ktlint"]
    mainClass.set("com.pinterest.ktlint.Main")
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
    args("-F", "src/**/*.kt", "**.kts", "!**/build/**")
}

tasks.register("detekt", JavaExec::class) {
    group = "verification"
    description = "Run Detekt static code analysis"

    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
    classpath = configurations["detekt"]

    val inputPath = "$projectDir/src"
    val excludePatterns = ".*/build/.*,.*/resources/.*"
    val configPath = "$rootDir/detekt.yml"

    if (!file(configPath).exists()) {
        throw GradleException("Detekt configuration file not found: $configPath")
    }

    args(
        "--input",
        inputPath,
        "--excludes",
        excludePatterns,
        "--config",
        configPath,
        "--report",
        "html:build/reports/detekt-report.html",
    )
}

tasks.named("check") {
    dependsOn(tasks.named("detekt"))
}
