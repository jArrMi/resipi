package com.dartharrmi.resipi.repositories

import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.repositories.db.RecipeDB

class SpoonacularLocalDataSource(private val recipeDB: RecipeDB
        /*private val recipesDao: RecipeDao,
        private val remoteKeyDao: RemoteKeyDao*/): ISpoonacularDataSource.Local {

    override fun saveRecipesToCache(recipes: List<Recipe>) = recipeDB.recipesDao().insertAll(recipes)

    override fun saveSingleRecipeToCache(recipe: Recipe) = recipeDB.recipesDao().insertSingle(recipe)
}