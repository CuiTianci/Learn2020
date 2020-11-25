package com.example.motionlayout

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        var appContext: Context? = null

        fun context(): Context {
            return appContext!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}