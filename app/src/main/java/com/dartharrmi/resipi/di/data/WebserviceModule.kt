package com.dartharrmi.resipi.di.data

import com.dartharrmi.resipi.webservice.utils.IPublicOkHttpClient
import com.dartharrmi.resipi.webservice.utils.PublicOkHttpClient
import org.koin.dsl.module
/**
 * This object provides the networking module to the DI provider. The module hold all of the dependencies
 * related to the networking layer.
 */
object WebserviceModule {

    val module = module {

        // Public HTTP client
        single<IPublicOkHttpClient> { PublicOkHttpClient() }
    }
}