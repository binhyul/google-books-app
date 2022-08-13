package com.example.google_books

import android.app.Application
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupUncaughtExceptionHandler()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun showToast(text: String) {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            when (e) {
                is UnknownHostException -> showToast("인터넷 연결을 확인해 주세요.")
                is IllegalArgumentException, is NullPointerException -> {
                    showToast("알 수 없는 오류가 발생했습니다")
                }
            }
        }
    }


}


