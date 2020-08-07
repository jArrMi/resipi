package com.dartharrmi.resipi.repositories

import com.dartharrmi.resipi.base.BaseUnitTest
import com.dartharrmi.resipi.webservice.api.ISpoonacularApi
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.koin.test.mock.declareMock

class SpoonacularRemoteDataSourceTest : BaseUnitTest() {

    private val api = declareMock<ISpoonacularApi>()
    private val remoteDataSource = spy(
        object : SpoonacularRemoteDataSource() {
            override fun spoonacularApi() = api
        }
    )

    @Test
    fun searchRecipes() {
        remoteDataSource.searchRecipes(QUERY, API_KEY, OFFSET, NUMBER)
        verify(remoteDataSource, times(1)).searchRecipes(QUERY, API_KEY, OFFSET, NUMBER)
        verify(api, times(1)).searchRecipes(QUERY, API_KEY, true, OFFSET, NUMBER)
    }

    @Test
    fun getRecipeDetails() {
        remoteDataSource.getRecipeDetails(RECIPE_ID, API_KEY)
        verify(remoteDataSource, times(1)).getRecipeDetails(RECIPE_ID, API_KEY)
        verify(api, times(1)).getRecipeDetails(RECIPE_ID, API_KEY)
    }
}