package com.dartharrmi.resipi.repositories.paging

import androidx.paging.PagingSource
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.usecases.IGetRecipesUseCase

/**
 * Incremental data loader for page-keyed content, where requests return keys for next/previous
 * pages.
 *
 * @param [getRecipesUseCase]   Use case for getting the list of recipes.
 */
class RecipePagingSource(private val getRecipesUseCase: IGetRecipesUseCase) : PagingSource<Int, Recipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        TODO("Not yet implemented")
    }
}