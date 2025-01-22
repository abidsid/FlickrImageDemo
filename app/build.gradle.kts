plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.flickr.demo.flickrdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dev.flickerimagedemo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose UI
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.tooling.preview)
//    kapt(libs.hilt.compiler)
    // Compose Material
    implementation(libs.ui)
    implementation(libs.androidx.paging.compose.v335)
    implementation(libs.androidx.navigation.compose.v285)
//    implementation (libs.androidx.hilt.lifecycle.viewmodel)
    implementation(libs.coil.image.load)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.coil.network.okhttp)
    // Retrofit
    implementation(libs.retrofit)
    // Gson Converter (for JSON to POJO conversion)
    implementation(libs.converter.gson)
    // OkHttp (for logging and network requests)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Coroutine support (if you're using Kotlin Coroutines)
    implementation(libs.adapter.rxjava2) // if you're using RxJava
    implementation(libs.retrofit2.adapter.rxjava3) // if you're using RxJava3
    implementation(libs.converter.moshi) // If you're using Moshi

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation("io.mockk:mockk:1.13.5")
}