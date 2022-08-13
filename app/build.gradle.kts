plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

repositories {
    maven {
        setUrl("../repository")
    }
    google()
    mavenCentral()
}

android {
    compileSdk = Versions.Android.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.google_books"
        minSdk = Versions.Android.minSdkVersion
        targetSdk = Versions.Android.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Deps.Android.X.core)
    implementation(Deps.Android.X.constraintLayout)
    implementation(Deps.Android.X.appCompat)
    implementation(Deps.Android.material)
    implementation(Deps.Navigation.navFragment)
    implementation(Deps.Navigation.navUI)
    implementation(Deps.Hilt.hiltAndroid)
    kapt(Deps.Hilt.hiltAndroidCompiler)
    implementation(Deps.Paging.runtime)
    implementation(Deps.Android.X.lifecycleRuntime)

    implementation(Deps.glide)
    kapt(Deps.glideCompiler)

    implementation(Deps.Network.apacheCommon)

    implementation(Deps.Network.retrofit)
    implementation(Deps.Network.retrofitConverter)
    implementation(Deps.Network.retrofitRx)

    implementation(Deps.Network.okHttpLogging)
    implementation(Deps.Network.okHttpUrlConnection)

    implementation(Deps.Network.loopjAsync)


    testImplementation(Deps.AndroidTest.junit)
    androidTestImplementation(Deps.AndroidTest.ext)
    androidTestImplementation(Deps.AndroidTest.Espresso.core)
}