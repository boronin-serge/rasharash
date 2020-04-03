package com.boronin.rasharash.main

import com.boronin.rasharash.base.BasePresenter
import com.boronin.rasharash.models.SearchResult
import com.boronin.rasharash.models.SongInfo
import com.boronin.rasharash.models.vendor.ITunesMetaData
import com.boronin.rasharash.models.vendor.VendorMetaData
import com.boronin.rasharash.utils.Constants
import com.boronin.rasharash.utils.progress
import io.reactivex.disposables.Disposable

class MainPresenter: BasePresenter<MainContract.View>(), MainContract.Presenter {
    private val interactor: MainContract.Interactor = MainInteractor()
    private var songInfo: SongInfo? = null
    private lateinit var subscriptions: Disposable

    // region MainContract.Presenter

    override fun searchSongByUrl(url: String?) {
        url?.let { it ->
            subscriptions = interactor.detectSongName(it)
                .progress{ view?.enableLoading(it) }
                .subscribe({ name ->
                    view?.preFillInput(name)
                    onSearchSongByName(name, ITunesMetaData.INSTANCE)
                }, {
                    view?.showError(Constants.TRACK_NOT_RECOGNIZED)
                })
        }
    }

    override fun onSearchSongByName(songName: String, vendor: VendorMetaData) {
        if (songName.isBlank()) return

        subscriptions = interactor.findSong(songName, vendor)
            .progress{ view?.enableLoading(it) }
            .subscribe({ songInfo ->
                view?.enableLoading(false)
                view?.showSearchResult(SearchResult(songInfo, vendor))
                this.songInfo = songInfo
            }, {
                view?.showError(Constants.LOADING_ERROR)
            })
    }

    override fun onShare() {
        try {
            view?.shareLink(songInfo?.url!!)
        } catch (e: Exception) {
            view?.showError(Constants.SMTH_WRONG_TEXT)
        }
    }

    // endregion
}