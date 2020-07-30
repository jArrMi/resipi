package com.dartharrmi.resipi.di

import com.dartharrmi.resipi.di.data.RepositoriesModule
import com.dartharrmi.resipi.di.data.WebserviceModule
import com.dartharrmi.resipi.di.domain.UseCasesModule
import com.dartharrmi.resipi.di.presentation.ViewModelsModule

/**
 * This object provides the application modules to the DI provider.
 */
object ApplicationModule {

    private val coreModules = listOf(
            WebserviceModule.module,
            RxModule.module,
            RepositoriesModule.module
    )

    private val resipiModules = listOf(
            UseCasesModule.module,
            ViewModelsModule.module
    )

    val applicationModules = coreModules + resipiModules
}