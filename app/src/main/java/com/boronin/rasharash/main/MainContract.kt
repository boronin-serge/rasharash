package com.boronin.rasharash.main

import com.boronin.rasharash.base.MvpPresenter
import com.boronin.rasharash.base.MvpView
import com.boronin.rasharash.models.SongInfo
import com.boronin.rasharash.models.vendor.VendorMetaData

interface MainContract {

    interface View: MvpView {
        fun initUI()
        fun enableLoading(isEnable: Boolean)
        fun enableShare(isEnable: Boolean)
        fun shareLink(url: String)
        fun showError(text: String)
        fun showMainText(text: String)
        fun showFoundedUrl(text: String)
    }

    interface Presenter: MvpPresenter<View> {
        fun onSearchSongName()
        fun onSearchSongUrl(songName: String, vendor: VendorMetaData)
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