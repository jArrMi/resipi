package com.dartharrmi.resipi.di.data

import androidx.room.Room
import com.dartharrmi.resipi.repositories.db.RecipeDB
import org.koin.dsl.module

object LocalStorageModule {

    const val DATABASE_NAME = "recipes"

    val module = module {

        single {
            Room.databaseBuilder(get(), RecipeDB::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
        }

        single { get<RecipeDB>().recipesDao() }

        single { get<RecipeDB>().remoteKeyDao() }
    }
}