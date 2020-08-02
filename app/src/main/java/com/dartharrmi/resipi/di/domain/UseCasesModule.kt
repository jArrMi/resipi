package com.dartharrmi.resipi.di.domain

import com.dartharrmi.resipi.usecases.GetRecipesUseCaseCase
import com.dartharrmi.resipi.usecases.IGetRecipesUseCase
import org.koin.dsl.module

object UseCasesModule {

    val module = module {

        // Get Recipes Use Case
        single<IGetRecipesUseCase> { GetRecipesUseCaseCase(get()) }
    }
}