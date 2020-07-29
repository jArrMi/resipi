package com.dartharrmi.resipi.ui.livedata

enum class Status { SUCCESS, FAILURE}

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<out T> private constructor(val status: Status, val data: T?, val throwable: Throwable?) {

    companion object {
        fun <T> success(data: T): Event<T> = Event(Status.SUCCESS, data = data, throwable = null)
        fun <T> failure(t: Throwable): Event<T> = Event(Status.FAILURE, data = null, throwable = t)
    }
}

