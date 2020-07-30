package com.dartharrmi.resipi.webservice

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

/**
 * Object responsible for creating instances of [Retrofit] for networking calls.
 */
object ResipiNetwork {

    private const val BASE_URL_NOT_PROVIDED_MESSAGE = "Base url is required for creating a new instance of Retrofit"

    /**
     * Creates a [Retrofit] object for consuming web services. [GsonConverterFactory] and [UnitConverterFactory] are
     * added as default to [Retrofit.Builder].
     *
     * @param baseUrl base web service url required. If empty it throws [IllegalArgumentException]
     * @param okHttpClient okHttpClient created using [YPOkHttpClient]
     * @param builder [Retrofit.Builder] optional parameter in case a custom configuration on [Retrofit] is required
     */
    @JvmStatic
    @JvmOverloads
    fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient, builder: Retrofit.Builder = Retrofit.Builder()): Retrofit {
        if (baseUrl.isEmpty()) {
            throw IllegalArgumentException(BASE_URL_NOT_PROVIDED_MESSAGE)
        }

        return builder.apply {
            baseUrl(baseUrl)
            client(okHttpClient)
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            addConverterFactory(UnitConverterFactory)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    private object UnitConverterFactory: Converter.Factory() {
        override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>,
                                           retrofit: Retrofit): Converter<ResponseBody, *>? {
            return if (type == Unit::class.java) UnitConverter else null
        }

        private object UnitConverter: Converter<ResponseBody, Unit> {
            override fun convert(value: ResponseBody) {
                value.close()
            }
        }
    }
}