package com.dartharrmi.resipi.webservice

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Object to create OKHttpClient and customized headers in http requests.
 */
object ResipiOkHttpClient {

    private var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
    private var timeOutInSeconds: Long = 60

    /**
     * Set log level of Http requests. Default value is [HttpLoggingInterceptor.Level.BODY]
     */
    @JvmStatic
    fun setLogLevel(logLevel: HttpLoggingInterceptor.Level) {
        this.logLevel = logLevel
    }

    /**
     * Set read and write time out http requests in SECONDS. Default value is 60 seconds
     */
    @JvmStatic
    fun setTimeOut(timeInSeconds: Long) {
        this.timeOutInSeconds = timeInSeconds
    }

    /**
     * Creates a public OkHttpClient to consume web services
     *
     * @param [context]     Android SDK runtime context.
     * @param [headers]     Optional customized headers in Map<String,String> type.
     */
    @JvmStatic
    @JvmOverloads
    fun createPublicOkHttpClient(context: Context, headers: Map<String, String> = mapOf()): OkHttpClient {
        val builder = OkHttpClient.Builder()
        setUpHeadersAndTimeOut(builder)
        return builder.build()
    }

    private fun setUpHeadersAndTimeOut(builder: OkHttpClient.Builder, currentHeaders: Map<String, String> = emptyMap()) {
        builder.apply {
            connectTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            readTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            writeTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                for (entry in currentHeaders.entries) {
                    requestBuilder.addHeader(entry.key, entry.value)
                }
                requestBuilder.method(original.method(), original.body())
                chain.proceed(requestBuilder.build())
            }
            addInterceptor(HttpLoggingInterceptor().also {
                setLogLevel(logLevel)
            })
        }
    }
}