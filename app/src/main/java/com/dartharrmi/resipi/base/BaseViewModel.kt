package com.dartharrmi.resipi.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dartharrmi.resipi.ui.livedata.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * A base [ViewModel] for preparing and managing the data for the UI layer. This class injects itself
 * with a [CompositeDisposable] object and disposes of it when it's about to be destroyed. It also
 * provides children with functions for showing and hiding a full-screen loading view. By implementing
 * the interface [KoinComponent] we make it easier for children to get their dependencies provided by
 * Koin.
 *
 * @see     [ViewModel].
 * @see     [KoinComponent].
 */
abstract class BaseViewModel : ViewModel(), KoinComponent {

    /**
     *
     */
    val compositeDisposable by inject<CompositeDisposable>()

    /**
     * An [Event] object for listening the loading status of the background operations.
     */
    val isLoadingEvent = MutableLiveData<Event<Boolean>>()

    /**
     * Shows a full-screen loading view when the [ViewModel] is executing a long-running task.
     */
    fun showProgress() {
        isLoadingEvent.value = Event.success(true)
    }

    /**
     * Hides the full-screen loading view when the [ViewModel] finishes executing A long-running task.
     */
    fun hideProgress() {
        isLoadingEvent.value = Event.success(false)
    }

    /**
     * @see     [ViewModel.onCleared].
     */
    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}