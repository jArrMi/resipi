package com.dartharrmi.resipi.di

import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.dsl.module

/**
 * This object provides the Rx modules to the DI provider.
 */
object RxModule {

    val module = module {
        single { CompositeDisposable() }
    }
}