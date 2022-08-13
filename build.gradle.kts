
buildscript {

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlinVersion}")

        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.Hilt.hiltAndroidVersion}")

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}")
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        google()
    }
}

tasks {
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}