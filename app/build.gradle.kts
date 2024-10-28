plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    namespace = "com.dicoding.event"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.event"
        minSdk = 24
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
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
// DataStore Preferences
    implementation (libs.androidx.datastore.preferences)

    // Lifecycle ViewModel & LiveData
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // Coroutines Core & Android
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Navigation Component
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // Glide
    implementation (libs.glide)
    annotationProcessor (libs.compiler)

    // Retrofit & Gson Converter
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Logging Interceptor
    implementation (libs.logging.interceptor)

    // Legacy Support for AndroidX
    implementation (libs.androidx.legacy.support.v4)

    // Fragment KTX
    implementation (libs.androidx.fragment.ktx)

    // Room Database
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler.v261)

    // Unit Testing
    testImplementation (libs.junit)

    // Android Instrumentation Testing
    androidTestImplementation (libs.androidx.junit.v113)
    androidTestImplementation (libs.androidx.espresso.core.v340)
}