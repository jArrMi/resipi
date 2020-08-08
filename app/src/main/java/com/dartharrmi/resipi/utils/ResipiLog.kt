@file:Suppress("unused")

package com.dartharrmi.resipi.utils

import android.util.Log
import com.dartharrmi.resipi.BuildConfig

/**
 * A logging utility class. By default this class only logs in debug builds, you can however specify a custom condition
 * for skip logging in other build types.
 */
object ResipiLog {

    private var prefix: String = "RSP-"
    private var forceDisable: Boolean = false
    private var loggingEnabled = !forceDisable && BuildConfig.DEBUG

    private const val MAX_LOG_TAG_LENGTH = 23

    @JvmStatic
    @JvmOverloads
    fun initialize(forceDisable: Boolean = false, prefix: String) {
        this.forceDisable = forceDisable
        this.prefix = if (prefix.isNotEmpty()) {
            prefix
        } else {
            this.prefix
        }
    }

    /**
     * Avoid generating the tag when obfuscating class names!
     */
    @JvmStatic
    fun makeLogTag(cls: Class<*>): String {
        return makeLogTag(cls.simpleName)
    }

    @JvmStatic
    fun makeLogTag(str: String): String {
        val logPrefixLength = str.length
        return if (str.length > MAX_LOG_TAG_LENGTH - logPrefixLength) {
            prefix + str.substring(0, MAX_LOG_TAG_LENGTH - logPrefixLength - 1)
        } else {
            prefix + str
        }
    }

    @JvmStatic
    fun LOGD(tag: String, message: String) {
        if (loggingEnabled) {
            if (Log.isLoggable(tag, Log.DEBUG)) {
                Log.d(tag, message)
            }
        }
    }

    @JvmStatic
    fun LOGD(tag: String, message: String, cause: Throwable) {
        if (loggingEnabled) {
            if (Log.isLoggable(tag, Log.DEBUG)) {
                Log.d(tag, message, cause)
            }
        }
    }

    @JvmStatic
    fun LOGV(tag: String, message: String) {
        if (loggingEnabled) {
            if (Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v(tag, message)
            }
        }
    }

    @JvmStatic
    fun LOGV(tag: String, message: String, cause: Throwable) {
        if (loggingEnabled) {
            if (Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v(tag, message, cause)
            }
        }
    }

    @JvmStatic
    fun LOGI(tag: String, message: String) {
        if (loggingEnabled) {
            Log.i(tag, message)
        }
    }

    @JvmStatic
    fun LOGI(tag: String, message: String, cause: Throwable) {
        if (loggingEnabled) {
            Log.i(tag, message, cause)
        }
    }

    @JvmStatic
    fun LOGW(tag: String, message: String) {
        Log.w(tag, message)
    }

    @JvmStatic
    fun LOGW(tag: String, message: String, cause: Throwable) {
        Log.w(tag, message, cause)
    }

    @JvmStatic
    fun LOGE(tag: String, message: String) {
        Log.e(tag, message)
    }

    @JvmStatic
    fun LOGE(tag: String, message: String, cause: Throwable) {
        Log.e(tag, message, cause)
    }
}