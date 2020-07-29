package com.dartharrmi.resipi.di

/**
 *
 */
object ApplicationModule {

    private val coreModules = listOf(
        WebserviceModule.module
    )

    val applicationModules = coreModules
}