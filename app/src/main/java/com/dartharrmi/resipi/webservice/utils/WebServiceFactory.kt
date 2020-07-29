package com.dartharrmi.resipi.webservice.utils

import android.content.Context
import com.dartharrmi.resipi.R
import com.dartharrmi.resipi.webservice.ResipiNetwork
import com.dartharrmi.resipi.webservice.api.ISpoonacularApi
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 */
object WebServiceFactory: KoinComponent {

    private val context by inject<Context>()
    private val publicOkHttpClient by inject<IPublicOkHttpClient>()

    /**
     *
     */
    fun createSpoonacularApi(): ISpoonacularApi {
        val retrofit = ResipiNetwork.createRetrofit(
                context.getString(R.string.spoonacular_base_url),
                publicOkHttpClient.create(context)
        )
        return retrofit.create(ISpoonacularApi::class.java)
    }
}