package com.dartharrmi.resipi.base

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.dartharrmi.resipi.di.ApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ResipiApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ResipiApp)
            modules(ApplicationModule.applicationModules)
        }
    }
}