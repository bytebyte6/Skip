package com.bytebyte6.skip

import android.app.Application
import com.balsikandar.crashreporter.CrashReporter

class App : Application (){
    override fun onCreate() {
        super.onCreate()
        CrashReporter.initialize(this)
    }
}