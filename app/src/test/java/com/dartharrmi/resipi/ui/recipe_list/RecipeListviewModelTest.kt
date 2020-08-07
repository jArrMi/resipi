package com.dartharrmi.resipi.ui.recipe_list

import com.dartharrmi.resipi.base.BaseUnitTest
import com.dartharrmi.resipi.usecases.IGetRecipesUseCase
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Test
import org.koin.test.mock.declareMock

class RecipeListviewModelTest : BaseUnitTest() {

    private val getRecipesUseCaseCase = declareMock<IGetRecipesUseCase>()
    private val viewModel = RecipesListViewModel(getRecipesUseCaseCase)

    @Test
    fun testViewModel() {
        whenever(getRecipesUseCaseCase.execute(QUERY)).doReturn(Flowable.just(mock()))

        viewModel.getRecipes(QUERY)
        verify(getRecipesUseCaseCase, times(1)).execute(QUERY)
    }
}