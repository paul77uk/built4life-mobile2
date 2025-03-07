package com.example.built4life2

import android.app.Application
import com.example.built4life2.data.Built4LifeDataContainer

class Built4LifeApplication : Application() {
    lateinit var container: Built4LifeDataContainer

    override fun onCreate() {
        super.onCreate()
        container = Built4LifeDataContainer(this)
    }
}