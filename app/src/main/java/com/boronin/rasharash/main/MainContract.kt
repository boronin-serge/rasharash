package com.boronin.rasharash.main

import com.boronin.rasharash.base.MvpPresenter
import com.boronin.rasharash.base.MvpView
import com.boronin.rasharash.models.SearchResult
import com.boronin.rasharash.models.SongInfo
import com.boronin.rasharash.models.vendor.ITunesMetaData
import com.boronin.rasharash.models.vendor.VendorMetaData
import io.reactivex.Observable

interface MainContract {

    interface View: MvpView {
        fun enableLoading(isEnable: Boolean)
        fun shareLink(url: String)
        fun showError(text: String)
        fun preFillInput(text: String)
        fun showSearchResult(searchResult: SearchResult)
    }

    interface Presenter: MvpPresenter<View> {
        fun onSearchSongByName(songName: String, vendor: VendorMetaData = ITunesMetaData.INSTANCE)
        fun searchSongByUrl(url: String?)
        fun onShare()
    }

    interface Interactor {
        fun detectSongName(songUrl: String?): Observable<String>
        fun findSong(songName: String, vendor: VendorMetaData): Observable<SongInfo>
    }
}