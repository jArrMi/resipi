package com.dartharrmi.resipi.repositories

import androidx.paging.PagingSource
import androidx.paging.rxjava2.RxPagingSource
import com.dartharrmi.resipi.base.BaseUnitTest
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.domain.exceptions.RecipesNotfoundException
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertFailsWith

/**
 * Unit test inspired from
 * https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-master-dev/paging/rxjava2/src/test/java/androidx/paging/RxPagingSourceTest.kt
 */
class RecipePagingSourceTest : BaseUnitTest() {

    private val recipe = Recipe()

    private val rxPagingSource = object : RxPagingSource<Int, Recipe>() {
        override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Recipe>> {
            return Single.create { emitter ->
                emitter.onSuccess(loadInternal(params))
            }
        }
    }
    private val pagingSource = object : PagingSource<Int, Recipe>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
            return loadInternal(params)
        }
    }

    private fun loadInternal(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult.Page<Int, Recipe> {
        val key = params.key!! // Intentionally fail on null keys
        return PagingSource.LoadResult.Page(
            List(1) { recipe },
            prevKey = key - params.loadSize,
            nextKey = key + params.loadSize
        )
    }

    @Test
    fun testLoad() = runBlocking {
        val params = PagingSource.LoadParams.Refresh(0, LOAD_SIZE, false, PAGE_SIZE)
        assertEquals(pagingSource.load(params), rxPagingSource.load(params))
    }

    /**
     * Surrounding the runBlocking block with the assertFailsWith block to allow suspend functions to be called and
     * execute the unit test
     */
    @Test
    fun testError() {
        assertFailsWith<java.lang.NullPointerException> {
            runBlocking {
                val params = PagingSource.LoadParams.Refresh<Int>(null, LOAD_SIZE, false, PAGE_SIZE)
                pagingSource.load(params)
                rxPagingSource.load(params)
            }
        }
    }

    @Test
    fun testException() {
        assertFailsWith<RecipesNotfoundException> {

        }
    }
}