package com.dartharrmi.resipi.repositories

import com.dartharrmi.resipi.domain.GetRecipesResponse
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.*
import com.dartharrmi.resipi.webservice.dto.response.GetRecipesResponseDTO
import com.dartharrmi.resipi.webservice.dto.response.toDomain
import io.reactivex.rxjava3.core.Single

class SpoonacularRepository(
    private val localDataSource: Local,
    private val remoteDataSource: Remote
) : Repository {

    override fun searchRecipes(
        apiKey: String, query: String, offset: Int, number: Int
    ): Single<GetRecipesResponse> {
        return remoteDataSource
            .searchRecipes(apiKey, query, offset, number)
            .map { t: GetRecipesResponseDTO? ->
                t?.toDomain()
            }/*.doOnSuccess {
                    localDataSource.saveRecipesToCache()
                }*/
    }
}