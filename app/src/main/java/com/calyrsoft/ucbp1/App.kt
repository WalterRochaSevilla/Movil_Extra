package com.calyrsoft.ucbp1

import android.app.Application
import com.calyrsoft.ucbp1.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@App)
            modules(appModule)  // ← SOLO appModule, como antes
        }
    }
}