package com.dartharrmi.resipi.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.dartharrmi.resipi.di.ApplicationModule
import com.dartharrmi.resipi.repositories.db.RecipeDB
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Base [Application] for the current app, initializes the dependency injection.
 */
class ResipiApp: MultiDexApplication() {

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