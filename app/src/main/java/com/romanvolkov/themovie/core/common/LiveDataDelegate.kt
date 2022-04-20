package com.romanvolkov.themovie.core.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.Observable

class LiveDataDelegate<T>(initialState: T? = null) {
    val liveData = MutableLiveData<T>()

    init {
        if (initialState != null) {
            liveData.value = initialState
        }
    }

    fun flow(lifecycleOwner: LifecycleOwner): Flowable<T?> = liveData.flow(lifecycleOwner)

    fun observable(lifecycleOwner: LifecycleOwner): Observable<T?> =
        liveData.observable(lifecycleOwner)

    fun observableNotNull(lifecycleOwner: LifecycleOwner): Observable<T> =
        liveData.observableNotNull(lifecycleOwner)

    fun flowNotNull(lifecycleOwner: LifecycleOwner): Flowable<T> =
        liveData.flowNotNull(lifecycleOwner)

    fun observe(owner: LifecycleOwner, observer: (T?) -> Unit) = liveData.observe(owner, observer)

    fun observeNotNull(owner: LifecycleOwner, observer: (T) -> Unit) =
        liveData.observeNotNull(owner, observer)

    var value: T?
        set(value) {
            liveData.value = value
        }
        get() = liveData.value
}
