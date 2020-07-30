package com.dartharrmi.resipi.di.data

import com.dartharrmi.resipi.repositories.ISpoonacularDataSource
import com.dartharrmi.resipi.repositories.SpoonacularLocalDataSource
import com.dartharrmi.resipi.repositories.SpoonacularRemoteDataSource
import com.dartharrmi.resipi.repositories.SpoonacularRepository
import org.koin.dsl.module

object RepositoriesModule {

    val module = module {

        // Spoonocular Repository
        single<ISpoonacularDataSource.Local> { SpoonacularLocalDataSource() }
        single<ISpoonacularDataSource.Remote> { SpoonacularRemoteDataSource() }
        single<ISpoonacularDataSource.Repository> { SpoonacularRepository(get(), get()) }
    }
}