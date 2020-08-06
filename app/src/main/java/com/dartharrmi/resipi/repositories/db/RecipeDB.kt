package com.dartharrmi.resipi.repositories.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dartharrmi.resipi.domain.InstructionStep
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.domain.RemoteKey
import com.dartharrmi.resipi.repositories.dao.RecipeDao
import com.dartharrmi.resipi.repositories.dao.RemoteKeyDao
import com.dartharrmi.resipi.repositories.db.converter.Converters
import com.dartharrmi.resipi.repositories.db.converter.InstructionsConverter
import com.dartharrmi.resipi.repositories.db.converter.NutritionConverter

@Database(
    entities = [Recipe::class, RemoteKey::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(value = [NutritionConverter::class, Converters::class, InstructionsConverter::class])
abstract class RecipeDB : RoomDatabase() {

    abstract fun recipesDao(): RecipeDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}