package com.dartharrmi.resipi.webservice.exception

/**
 * Exception thrown when the application tries to create a [Retrofit] without providing a
 * valid base URL string.
 *
 * [message]    Detailed message of the exception.
 */
class BaseUrlNotProvidedException(message: String): IllegalArgumentException(message)