package com.dartharrmi.resipi.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dartharrmi.resipi.ui.livedata.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 */
abstract class BaseViewModel : ViewModel(), KoinComponent {

    /**
     *
     */
    val compositeDisposable by inject<CompositeDisposable>()

    /**
     *
     */
    val isLoadingEvent = MutableLiveData<Event<Boolean>>()

    /**
     *
     */
    fun showProgress() {
        isLoadingEvent.value = Event.success(true)
    }

    /**
     *
     */
    fun hideProgress() {
        isLoadingEvent.value = Event.success(false)
    }

    /**
     *
     */
    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}