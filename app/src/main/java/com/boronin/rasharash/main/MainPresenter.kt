package com.boronin.rasharash.main

import com.boronin.rasharash.base.BasePresenter
import com.boronin.rasharash.models.vendor.ITunesMetaData
import com.boronin.rasharash.models.vendor.VendorMetaData
import com.boronin.rasharash.utils.Constants
import com.boronin.rasharash.utils.progress
import io.reactivex.disposables.Disposable

class MainPresenter: BasePresenter<MainContract.View>(), MainContract.Presenter {
    private val interactor: MainContract.Interactor = MainInteractor()
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
            .subscribe({ list ->
                view?.enableLoading(false)
                view?.showSearchResult(list ?: listOf())
            }, {
                view?.showError(Constants.LOADING_ERROR)
            })
    }

    // endregion
}