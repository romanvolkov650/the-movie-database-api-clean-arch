package com.romanvolkov.themovie.core.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.Observable

class LiveDataNotNull<T>(initialState: T) {
	val liveData = MutableLiveData<T>()

	init {
		liveData.value = initialState
	}

	fun flow(lifecycleOwner: LifecycleOwner): Flowable<T> = liveData.flowNotNull(lifecycleOwner)

	fun observe(owner: LifecycleOwner, observer: (T) -> Unit) = liveData.observeNotNull(owner, observer)
    fun observableNotNull(lifecycleOwner: LifecycleOwner): Observable<T> = liveData.observableNotNull(lifecycleOwner)

	var value: T
		set(value) {
			liveData.value = value
		}
		get() = liveData.value!!
}
