package com.dartharrmi.resipi.base

import com.dartharrmi.resipi.usecases.IGetRecipesUseCase
import com.dartharrmi.resipi.webservice.api.ISpoonacularApi
import com.dartharrmi.resipi.webservice.utils.IPublicOkHttpClient
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock

object TestModules: KoinTest {

    val modules = module(override = true) {

        single<IPublicOkHttpClient> { declareMock() }

        single<ISpoonacularApi> { declareMock() }

        single<IGetRecipesUseCase> { declareMock() }
    }
}