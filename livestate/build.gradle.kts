import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        minSdkVersion(15)
        targetSdkVersion(28)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions{
        this as KotlinJvmOptions
        this.freeCompilerArgs += "-XXLanguage:+InlineClasses"
    }
}

dependencies {
    val lifecycleVersion = "2.0.0"

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${rootProject.extra["kotlinVersion"]}")
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    testImplementation("junit:junit:4.12")
    testImplementation ("androidx.arch.core:core-testing:$lifecycleVersion")
    testImplementation ("org.mockito:mockito-core:3.0.0")
    testImplementation ("com.google.truth:truth:1.0")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
