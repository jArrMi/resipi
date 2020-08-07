package com.dartharrmi.resipi.ui.livedata

import com.dartharrmi.resipi.base.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class EventTest: BaseUnitTest() {

    companion object {
        const val ERROR_DATA_NOT_NULL_VALUE = "data should be null"
        const val ERROR_DATA_NULL_VALUE = "data shouldn't be null"

        const val ERROR_THROWABLE_NOT_NULL_VALUE = "throwable should be null"
        const val ERROR_THROWABLE_NULL_VALUE = "throwable shouldn't be null"

        const val ERROR_INVALID_STATUS_SUCCESS = "Status should de SUCCESS"
        const val ERROR_INVALID_STATUS_FAILURE = "Status should de FAILURE"
    }

    @Test
    fun successTest() {
        val event = Event.success(Any())
        assertNotNull(ERROR_DATA_NULL_VALUE, event.data)
        assertNull(ERROR_THROWABLE_NOT_NULL_VALUE, event.throwable)
        assertEquals(ERROR_INVALID_STATUS_SUCCESS, Status.SUCCESS, event.status)
    }

    @Test
    fun failureTest() {
        val event = Event.failure<Any>(Throwable())
        assertNotNull(ERROR_THROWABLE_NULL_VALUE, event.throwable)
        assertNull(ERROR_DATA_NOT_NULL_VALUE, event.data)
        assertEquals(ERROR_INVALID_STATUS_FAILURE, Status.FAILURE, event.status)
    }

}