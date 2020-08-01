package com.dartharrmi.resipi.webservice.api

import com.dartharrmi.resipi.webservice.dto.response.GetRecipesResponseDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface for defining the methods to communicate with the Spoonacular API.
 */
interface ISpoonacularApi {

    private companion object {
        const val SEARCH = "recipes/complexSearch"
        const val GET_RECIPE_DETAILS = "recipes/{recipeId}/information"

        const val PARAM_QUERY = "query"
        const val PARAM_OFFSET = "offset"
        const val PARAM_NUMBER = "number"
        const val PARAM_RECIPE_NUTRITION = "addRecipeNutrition"
        const val PARAM_API_KEY = "apiKey"
        const val PATH_RECIPE_ID = "recipeId"
    }

    @GET(SEARCH)
    fun searchRecipes(
        @Query(PARAM_API_KEY) apiKey: String,
        @Query(PARAM_QUERY) query: String,
        @Query(PARAM_RECIPE_NUTRITION) addRecipeNutrition: Boolean,
        @Query(PARAM_OFFSET) offset: Int,
        @Query(PARAM_NUMBER) number: Int
    ): Single<GetRecipesResponseDTO>

    @GET(GET_RECIPE_DETAILS)
    fun getRecipeDetails(
        @Path(PATH_RECIPE_ID) recipeId: Long, @Query(PARAM_API_KEY) apiKey: String
    ): Single<GetRecipesResponseDTO>
}