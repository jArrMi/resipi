package com.dartharrmi.resipi.base

import android.content.Context
import com.dartharrmi.resipi.webservice.utils.IPublicOkHttpClient
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class TestPublicOkHttpClient : IPublicOkHttpClient {
    override fun create(context: Context) =
        CustomOkHttpClient.get()
}

object CustomOkHttpClient {

    fun get(): OkHttpClient {
        return try {
            val builder = OkHttpClient.Builder()
            builder.hostnameVerifier { _, _ -> true }
            builder.connectTimeout(10, TimeUnit.SECONDS)
            builder.readTimeout(10, TimeUnit.SECONDS)
            builder.writeTimeout(10, TimeUnit.SECONDS)

            builder.build()
        } catch (e: Exception) {
            OkHttpClient.Builder().build()
        }
    }

}