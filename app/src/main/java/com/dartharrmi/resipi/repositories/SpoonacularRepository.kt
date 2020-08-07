package com.dartharrmi.resipi.repositories

import com.dartharrmi.resipi.domain.GetRecipesResponse
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.Local
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.Remote
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.Repository
import com.dartharrmi.resipi.webservice.dto.response.GetRecipeIngredientsDTO
import com.dartharrmi.resipi.webservice.dto.response.GetRecipesResponseDTO
import com.dartharrmi.resipi.webservice.dto.response.toDomain
import io.reactivex.Single

class SpoonacularRepository(
        private val localDataSource: Local,
        private val remoteDataSource: Remote
): Repository {

    override fun searchRecipes(
            apiKey: String, query: String, offset: Int, number: Int
    ): Single<GetRecipesResponse> {
        return remoteDataSource
                .searchRecipes(apiKey, query, offset, number)
                .map { t: GetRecipesResponseDTO? ->
                    t?.toDomain()
                }
    }

    override fun getRecipeDetails(recipeId: Long, apiKey: String): Single<GetRecipeIngredientsDTO> {
        return remoteDataSource.getRecipeDetails(recipeId, apiKey)
    }

    override fun saveRecipes(recipes: List<Recipe>) = localDataSource.saveRecipesToCache(recipes)

    override fun saveSingleRecipe(recipe: Recipe) = localDataSource.saveSingleRecipeToCache(recipe)
}