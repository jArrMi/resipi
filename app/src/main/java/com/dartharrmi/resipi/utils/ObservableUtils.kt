package com.dartharrmi.resipi.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

inline fun <reified T> Gson.fromJson(json: String): T =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

/**
 * Default subscriber for UI observables
 */
inline fun <T> Observable<T>.defaultUISubscriber(
    crossinline onNext: (data: T) -> Unit,
    compositeDisposable: CompositeDisposable? = null
): Disposable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            onNext(it)
        }, Throwable::printStackTrace).also {
            compositeDisposable?.add(it)
        }
}

/**
 * Default subscriber for UI observables
 */
fun <T> Observable<T>.applyIoMain(): Observable<T> = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

/**
 * Default subscriber for UI observables
 */
fun <T> Single<T>.applyIoMain(): Single<T> = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

/**
 * Default subscriber for UI observables
 */
fun Completable.applyIoMain(): Completable = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

/**
 * Default subscriber for UI observables
 */
fun <T> Flowable<T>.applyIoMain(): Flowable<T> = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

/**
 * Default subscriber for UI observables
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable) = compositeDisposable.add(this)
