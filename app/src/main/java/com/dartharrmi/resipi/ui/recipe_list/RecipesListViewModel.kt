package com.dartharrmi.resipi.ui.recipe_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.dartharrmi.resipi.base.BaseViewModel
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.usecases.IGetRecipesUseCase
import io.reactivex.Flowable

class RecipesListViewModel(private val getRecipesUseCaseCase: IGetRecipesUseCase): BaseViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flowable<PagingData<Recipe>>? = null

    fun getRecipes(queryString: String): Flowable<PagingData<Recipe>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }

        currentQueryValue = queryString
        val newResult: Flowable<PagingData<Recipe>> = getRecipesUseCaseCase.execute(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult

        return newResult
    }

}