package com.dartharrmi.resipi.webservice.utils

import android.content.Context
import com.dartharrmi.resipi.webservice.ResipiOkHttpClient
import okhttp3.OkHttpClient

/**
 *
 */
interface IPublicOkHttpClient {
    fun create(context: Context): OkHttpClient
}

/**
 * Concrete implementation for [IPublicOkHttpClient]
 */
class PublicOkHttpClient: IPublicOkHttpClient {
    override fun create(context: Context) = ResipiOkHttpClient.createPublicOkHttpClient(context)
}