package com.romanvolkov.themovie.core

import androidx.lifecycle.ViewModel
import com.romanvolkov.themovie.BuildConfig
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

abstract class DispatchViewModel : ViewModel() {
    val dispatcher: PublishSubject<Action> = PublishSubject.create()

    protected open suspend fun reduce(action: Action) {}

    private suspend fun dispatch(action: Action) {
        try {
            action.let {
                dispatcher.onNext(it)
                reduce(it)
            }
        } catch (t: Exception) {
            Action.Error(t).let {
                dispatcher.onNext(it)
                reduce(it)
            }
        }
    }

    fun dispatch(action: suspend () -> Action) {
        CoroutineScope(Dispatchers.Main).launch {
            val actionObject = action()
            dispatch(action())
            if (BuildConfig.DEBUG) {
                Timber.d("dispatch -> $actionObject")
            }
        }
    }

    inline fun <reified T : Any> listen(): Observable<T> = dispatcher
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .ofType(T::class.java)
}