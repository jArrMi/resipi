package com.dartharrmi.resipi.di

/**
 * This object provides the application modules to the DI provider.
 */
object ApplicationModule {

    private val coreModules = listOf(
        WebserviceModule.module,
        RxModule.module
    )

    val applicationModules = coreModules
}