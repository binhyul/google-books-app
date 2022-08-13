object Deps {
    object Android {
        object X {
            const val core = "androidx.core:core-ktx:${Versions.coreVersion}"
            const val lifecycleRuntime =
                "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
            const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"


            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
        }

        const val material = "com.google.android.material:material:${Versions.materialVersion}"
    }

    object Paging {
        const val runtime = "androidx.paging:paging-runtime:${Versions.pagingVersion}"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
        const val retrofitConverter =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
        const val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"

        const val okHttpLogging =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"
        const val okHttpUrlConnection =
            "com.squareup.okhttp3:okhttp-urlconnection:${Versions.okHttpVersion}"
        const val loopjAsync = "com.loopj.android:android-async-http:${Versions.loopjAsyncVersion}"
        const val apacheCommon = "org.apache.commons:commons-lang3:${Versions.apacheCommonVersion}"
    }

    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    object AndroidTest {
        const val junit = "junit:junit:${Versions.AndroidTest.junitVersion}"
        const val core = "androidx.test:core:${Versions.AndroidTest.coreVersion}"
        const val runner = "androidx.test:runner:${Versions.AndroidTest.runnerVersion}"
        const val rules = "androidx.test:rules:${Versions.AndroidTest.rulesVersion}"
        const val ext = "androidx.test.ext:junit-ktx:${Versions.AndroidTest.extVersion}"

        object Espresso {
            const val core =
                "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espressoVersion}"
        }
    }

    object Hilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.Hilt.hiltAndroidVersion}"
        const val hiltAndroidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.Hilt.hiltAndroidVersion}"
    }

    object Navigation {
        const val navFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
        const val navUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
        const val navTest = "androidx.navigation:navigation-testing:${Versions.navigationVersion}"
    }

}