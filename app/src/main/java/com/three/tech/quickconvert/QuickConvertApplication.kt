package com.three.tech.quickconvert

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuickConvertApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("QuickConvertApplication", "onCreate: Called")
    }
}