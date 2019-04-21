package com.boronin.rasharash.main

import com.boronin.rasharash.models.SongInfo
import com.boronin.rasharash.base.BasePresenter
import com.boronin.rasharash.models.SearchResult
import com.boronin.rasharash.models.vendor.ITunesMetaData
import com.boronin.rasharash.models.vendor.VendorMetaData
import com.boronin.rasharash.utils.Constants

class MainPresenter: BasePresenter<MainContract.View>(), MainContract.Presenter,
    MainContract.InteractorCallback {
    private val interactor: MainContract.Interactor = MainInteractor(this)
    private var inputUrl: String? = null
    private var songInfo: SongInfo? = null

    override fun viewIsReady() {
        with(view!!) {
            initUI()
        }
    }

    override fun onUpdateInput(input: String?) {
        input?.let {
            inputUrl = it
            onSearchSongName()
        }
    }

    override fun onSearchSongName() {
        view?.enableLoading(true)
        interactor.detectSongName(inputUrl)
    }

    override fun onSearchSongUrl(songName: String, vendor: VendorMetaData) {
        if (songName.isBlank()) return

        view?.enableLoading(true)
        interactor.findSong(songName, vendor)
    }

    override fun onSearchSongUrl(songName: String) {
        onSearchSongUrl(songName, ITunesMetaData.INSTANCE)
    }

    override fun onShare() {
        try {
            view?.shareLink(songInfo?.url!!)
        } catch (e: Exception) {
            view?.showError(Constants.SMTH_WRONG_TEXT)
        }
    }

    // Interactor's callbacks

    override fun onSongNameDetected(name: String) {
        view?.showMainText(name)
        onSearchSongUrl(name, ITunesMetaData.INSTANCE)
    }

    override fun onSongFound(vendor: VendorMetaData, songInfo: SongInfo) {
        view?.enableLoading(false)
        view?.showFoundedSong(SearchResult(songInfo, vendor))
        this.songInfo = songInfo
    }

    override fun onError(text: String) {
        var error = text
        if (error.isBlank()) error = Constants.SMTH_WRONG_TEXT
        view?.showError(error)
        view?.enableLoading(false)
    }
}