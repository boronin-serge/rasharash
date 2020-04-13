package com.boronin.rasharash.main

import com.boronin.rasharash.base.BasePresenter
import com.boronin.rasharash.utils.Constants
import com.boronin.rasharash.utils.progress
import io.reactivex.disposables.Disposable

class MainPresenter: BasePresenter<MainContract.View>(), MainContract.Presenter {
    private val interactor: MainContract.Interactor = MainInteractor()
    private var subscriptions: Disposable? = null

    override fun attachView(v: MainContract.View) {
        super.attachView(v)

        view?.setSearchHint()
        view?.showHint(true)
    }

    // region MainContract.Presenter

    override fun searchSongByUrl(url: String?) {
        subscriptions?.dispose()
        url?.let { it ->
            subscriptions = interactor.detectSongName(it)
                .progress{ view?.enableLoading(it) }
                .subscribe({ name ->
                    view?.preFillInput(name)
                    onSearchSongByName(name)
                }, {
                    view?.showError(Constants.TRACK_NOT_RECOGNIZED)
                })
        }
    }

    override fun onSearchSongByName(songName: String) {
        if (songName.isBlank()) return

        subscriptions = interactor.findSong(songName)
            .progress{ view?.enableLoading(it) }
            .subscribe({ list ->
                view?.showSearchResult(list ?: listOf())
                view?.setNotFoundHint()
                view?.showHint(list.isNullOrEmpty())
            }, {
                view?.showError(Constants.LOADING_ERROR)
            })
    }

    // endregion
}

