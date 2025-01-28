plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)


    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.navigation)


    //   id ("kotlin-kapt")

    id ("kotlin-parcelize")


}

android {
    namespace = "com.example.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movieapp"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }


    buildFeatures {
        viewBinding = true
        buildConfig = true
    }




}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)



    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.data.store)  // برای استفاده از Data Store
    implementation(libs.data.store.preferences)  // برای استفاده از Preferences Data Store


    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)


    implementation(libs.navigation.fragment)

    implementation(libs.navigation.ui)

    implementation(libs.lottie)

    implementation(libs.coil)

    implementation(libs.swipe)

    implementation(libs.okHttp)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    implementation(libs.gson)

    implementation(libs.calligraphy)
    implementation(libs.viewpump)


    implementation(libs.dynamicSizes)



    implementation(libs.pagingg)





    implementation(libs.mpAndroidChart)


    implementation(libs.youtubeplayer)




    implementation(libs.carouselrecyclerview)

    implementation(libs.circleindicator)




}