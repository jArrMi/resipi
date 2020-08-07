package com.dartharrmi.resipi.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.Repository
import com.dartharrmi.resipi.repositories.paging.RecipePagingSource
import io.reactivex.Flowable

interface IGetRecipesUseCase {

    fun execute(query: String): Flowable<PagingData<Recipe>>
}

class GetRecipesUseCaseCase(private val repository: Repository) : IGetRecipesUseCase {

    override fun execute(query: String): Flowable<PagingData<Recipe>> =
        Pager(PagingConfig(pageSize = 10)) {
            RecipePagingSource(query, repository)
        }.flowable
}