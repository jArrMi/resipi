package com.dartharrmi.resipi.di

import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.dsl.module

object RxModule {

    val module = module {
        single<CompositeDisposable> { CompositeDisposable() }
    }
}