package com.dartharrmi.resipi.repositories

import com.dartharrmi.resipi.domain.Recipe

class SpoonacularLocalDataSource: ISpoonacularDataSource.Local {

    override fun saveRecipesToCache(recipes: List<Recipe>) = Unit
}