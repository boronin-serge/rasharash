package com.boronin.rasharash.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.schedulers(): Observable<T> = compose {
    it.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.progress(progressFun: (Boolean) -> Unit): Observable<T> = compose {
    it.doOnSubscribe { progressFun.invoke(true) }
        .doAfterTerminate { progressFun.invoke(false) }
}