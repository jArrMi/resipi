package com.dartharrmi.resipi.di

import com.dartharrmi.resipi.base.BaseUnitTest
import org.junit.Test
import org.koin.test.check.checkModules

class AppModuleTest : BaseUnitTest() {

    @Test
    fun testDependenceInjectionTree() {

        getKoin().checkModules()
    }

}