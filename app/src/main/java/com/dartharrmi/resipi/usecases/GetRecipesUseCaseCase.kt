package com.dartharrmi.resipi.usecases

import com.dartharrmi.resipi.domain.GetRecipesResponse
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.Repository
import io.reactivex.rxjava3.core.Single

interface IGetRecipesUseCase {
    fun execute(apiKey: String, query: String, offset: Int, number: Int): Single<GetRecipesResponse>
}

class GetRecipesUseCaseCase(private val repository: Repository): IGetRecipesUseCase {

    override fun execute(apiKey: String, query: String, offset: Int, number: Int): Single<GetRecipesResponse> {
        return repository.searchRecipes(apiKey, query, offset, number)
    }
}