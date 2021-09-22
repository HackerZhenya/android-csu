package com.hackerzhenya.phones

import android.app.Application
import com.hackerzhenya.phones.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            androidFileProperties()
            modules(appModules)
        }
    }
}