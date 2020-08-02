package com.dartharrmi.resipi.repositories

import com.dartharrmi.resipi.domain.GetRecipesResponse
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.webservice.api.ISpoonacularApi
import com.dartharrmi.resipi.webservice.dto.response.GetRecipeIngredientsDTO
import com.dartharrmi.resipi.webservice.dto.response.GetRecipesResponseDTO
import io.reactivex.Single

interface ISpoonacularDataSource {

    interface Local {
        fun saveRecipesToCache(recipes: List<Recipe>)
    }

    interface Remote {
        fun spoonacularApi(): ISpoonacularApi

        fun searchRecipes(
            apiKey: String, query: String, offset: Int, number: Int
        ): Single<GetRecipesResponseDTO>

        fun getRecipeDetails(recipeId: Long, apiKey: String): Single<GetRecipeIngredientsDTO>
    }

    interface Repository {
        fun searchRecipes(
            apiKey: String, query: String, offset: Int, number: Int
        ): Single<GetRecipesResponse>

        fun getRecipeDetails(recipeId: Long, apiKey: String): Single<GetRecipeIngredientsDTO>
    }
}