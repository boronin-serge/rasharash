package com.boronin.rasharash.main

import com.boronin.rasharash.SongInfo
import com.boronin.rasharash.base.MvpPresenter
import com.boronin.rasharash.base.MvpView
import com.boronin.rasharash.vendor.VendorMetaData

interface MainContract {

    interface View: MvpView {
        fun initUI()
        fun enableLoading(isEnable: Boolean)
        fun shareLink(url: String)
        fun showError()
        fun setMainText(text: String)
    }

    interface Presenter: MvpPresenter<View> {
        fun onSearchSongName()
        fun onUpdateInput(input: String?)
        fun onShare()
    }

    interface Interactor {
        fun detectSongName(songUrl: String?)
        fun findSong(songName: String, vendor: VendorMetaData)
    }

    interface InteractorCallback {
        fun onSongNameDetected(name: String)
        fun onSongFound(vendor: VendorMetaData, songInfo: SongInfo)
        fun onError(text: String)

    }
}