package com.boronin.rasharash.main

import com.boronin.rasharash.base.MvpPresenter
import com.boronin.rasharash.base.MvpView
import com.boronin.rasharash.models.song.SongInfo
import io.reactivex.Observable

interface MainContract {

    interface View: MvpView {
        fun enableLoading(isEnable: Boolean)
        fun shareLink(url: String)
        fun showError(text: String)
        fun preFillInput(text: String)
        fun setNotFoundHint()
        fun setSearchHint()
        fun showHint(show: Boolean)
        fun showSearchResult(searchResult: List<SongInfo>)
    }

    interface Presenter: MvpPresenter<View> {
        fun onSearchSongByName(songName: String)
        fun searchSongByUrl(url: String?)
    }

    interface Interactor {
        fun detectSongName(songUrl: String?): Observable<String>
        fun findSong(songName: String): Observable<List<SongInfo>?>
    }
}