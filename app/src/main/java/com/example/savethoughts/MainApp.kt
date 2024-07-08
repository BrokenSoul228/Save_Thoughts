package com.example.savethoughts

import android.app.Application
import com.example.savethoughts.di.DaggerAppComponent

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.create()
    }
}