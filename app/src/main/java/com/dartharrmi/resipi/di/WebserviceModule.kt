package com.dartharrmi.resipi.di

import com.dartharrmi.resipi.webservice.utils.IPublicOkHttpClient
import com.dartharrmi.resipi.webservice.utils.PublicOkHttpClient
import org.koin.dsl.module

/**
 *
 */
object WebserviceModule {

    /**
     *
     */
    val module = module {

        // Public HTTP client
        single<IPublicOkHttpClient> { PublicOkHttpClient() }
    }
}