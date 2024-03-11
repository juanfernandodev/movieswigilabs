plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.juanferdev.pruebaingresomovieswigilabs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.juanferdev.pruebaingresomovieswigilabs"
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
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    val coilVersion = "2.6.0"
    val lifecycleVersion = "2.7.0"
    val activityVersion = "1.8.2"
    val retrofitVersion = "2.9.0"
    val jUnitVersion = "4.13.2"
    val coreVersion = "1.12.0"
    val appCompactVersion = "1.6.1"
    val materialDesignVersion = "1.11.0"
    val constraintLayoutVersion = "2.1.4"
    val roomVersion = "2.6.1"
    val hiltVersion = "2.50"
    val testng = "6.9.6"
    val kotlinxCoroutines = "1.7.3"
    implementation("androidx.core:core-ktx:$coreVersion")
    implementation("androidx.appcompat:appcompat:$appCompactVersion")
    implementation("com.google.android.material:material:$materialDesignVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-ktx:$activityVersion")
    //Coil Images
    implementation("io.coil-kt:coil:$coilVersion")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    //Room
    implementation("androidx.room:room-ktx:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    //Dependency Injection with hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")


    testImplementation("junit:junit:$jUnitVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutines")
    testImplementation("org.testng:testng:$testng")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}