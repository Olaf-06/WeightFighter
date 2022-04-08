package com.example.weightfight

import android.app.Application
import com.onesignal.OneSignal

class App : Application() {

    private val ONESIGNAL_APP_ID = "3a94f19a-10c7-4144-8ea3-38242cd337fd"
    private val APPSFLYER_ID = "weightfighter-655f5"

    override fun onCreate() {
        super.onCreate()

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}