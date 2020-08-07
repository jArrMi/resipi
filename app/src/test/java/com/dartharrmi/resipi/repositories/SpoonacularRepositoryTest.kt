package com.dartharrmi.resipi.repositories

import com.dartharrmi.resipi.base.BaseUnitTest
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock

class SpoonacularRepositoryTest : BaseUnitTest() {

    private val localDataSource = declareMock<ISpoonacularDataSource.Local>()
    private val remoteDataSource = declareMock<ISpoonacularDataSource.Remote>()

    private val repository by inject<ISpoonacularDataSource.Repository>()

    @Test
    fun testSearchRecipes() {
        repository.searchRecipes(API_KEY, QUERY, OFFSET, NUMBER)

        verify(remoteDataSource, times(1)).searchRecipes(API_KEY, QUERY, OFFSET, NUMBER)
        verifyZeroInteractions(localDataSource)
    }

    @Test
    fun testGetRecipeDetails() {
        repository.getRecipeDetails(RECIPE_ID, API_KEY)

        verify(remoteDataSource, times(1)).getRecipeDetails(RECIPE_ID, API_KEY)
        verifyZeroInteractions(localDataSource)
    }
}