package com.dartharrmi.resipi.webservice.api

import com.dartharrmi.resipi.webservice.dto.response.GetRecipesResponseDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for defining the methods to communicate with the Spoonacular API.
 */
interface ISpoonacularApi {

    private companion object {
        const val SEARCH = "recipes/search"

        const val PARAM_QUERY = "query"
        const val PARAM_OFFSET = "offset"
        const val PARAM_NUMBER = "number"
        const val PARAM_API_KEY = "apiKey"
    }

    @GET(SEARCH)
    fun searchRecipes(
        @Query(PARAM_API_KEY) apiKey: String, @Query(PARAM_QUERY) query: String,
        @Query(PARAM_OFFSET) offset: Int, @Query(PARAM_NUMBER) number: Int
    ): Single<GetRecipesResponseDTO>
}