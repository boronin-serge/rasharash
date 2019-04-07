package com.boronin.rasharash.base

abstract class BasePresenter<V: MvpView>: MvpPresenter<V> {

    protected var view: V? = null

    override fun attachView(v: V) {
        view = v
    }

    override fun detachView() {
        view = null
    }
}