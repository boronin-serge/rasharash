package com.boronin.rasharash.main

import com.boronin.rasharash.base.MvpPresenter
import com.boronin.rasharash.base.MvpView

interface MainContract {

    interface View: MvpView {
        fun initUI()
        fun enableLoading(isEnable: Boolean)
        fun shareLink(url: String)
        fun showError()
        fun setMainText(text: String)
        fun readInputUrl()
    }

    interface Presenter: MvpPresenter<View> {
        fun onSearchSongName()
        fun onSearchItunesSong()
        fun onUpdateInput(input: String?)
        fun onShare()
    }
}