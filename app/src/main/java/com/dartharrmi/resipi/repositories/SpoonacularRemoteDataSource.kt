package com.dartharrmi.resipi.repositories

import com.dartharrmi.resipi.webservice.api.ISpoonacularApi
import com.dartharrmi.resipi.webservice.utils.WebServiceFactory

class SpoonacularRemoteDataSource: ISpoonacularDataSource.Remote {

    override fun spoonacularApi(): ISpoonacularApi = WebServiceFactory.createSpoonacularApi()

    override fun searchRecipes(apiKey: String, query: String, offset: Int, number: Int) = spoonacularApi()
            .searchRecipes(apiKey, query, true, offset, number)
}