package com.boronin.rasharash.base

interface MvpPresenter<V: MvpView> {
    fun attachView(v: V)
    fun detachView()
    fun viewIsReady()
}