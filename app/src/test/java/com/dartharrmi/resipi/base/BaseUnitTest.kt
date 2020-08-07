package com.dartharrmi.resipi.base

import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApp::class)
abstract class BaseUnitTest: KoinTest {

    internal companion object {
        const val QUERY = "query"
        const val API_KEY = "apiKey"
        const val OFFSET: Int = 10
        const val NUMBER: Int = 10
        const val RECIPE_ID = 1L
        const val LOAD_SIZE = 4
        const val PAGE_SIZE = 4
    }

    protected val context: Context by lazy { InstrumentationRegistry.getInstrumentation().targetContext }

    init {
        startKoin {
            androidContext(context)
        }
        loadKoinModules(TestModules.modules)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Before
    fun setUpRx() {
        RxJavaPlugins.setIoSchedulerHandler { AndroidSchedulers.mainThread() }
    }

    fun <F: Fragment> fragmentWithMockNavController(fragment: F): F = fragment.also {
        it.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
            if (viewLifecycleOwner != null) {
                Navigation.setViewNavController(it.requireView(), mock())
            }
        }
    }
}


