package com.boronin.rasharash.main

import com.boronin.rasharash.SongInfo
import com.boronin.rasharash.SongUrlDetector
import com.boronin.rasharash.base.BasePresenter
import com.boronin.rasharash.vendor.ITunesMetaData
import com.boronin.rasharash.vendor.VendorMetaData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter: BasePresenter<MainContract.View>(), MainContract.Presenter,
    MainContract.InteractorCallback {
    private val interactor: MainContract.Interactor = MainInteractor(this)
    private var inputUrl: String? = null
    private var songInfo: SongInfo? = null

    override fun viewIsReady() {
        with(view!!) {
            initUI()
            setMainText(inputUrl ?: "")
        }
    }

    override fun onUpdateInput(input: String?) {
        inputUrl = input
        onSearchSongName()
    }

    override fun onSearchSongName() {
        view?.enableLoading(true)
        interactor.detectSongName(inputUrl)
    }

    override fun onShare() {
        try {
            view?.shareLink(songInfo?.url!!)
        } catch (e: Exception) {
            view?.showError()
        }
    }

    // Interactor's callbacks

    override fun onSongNameDetected(name: String) {
        view?.enableLoading(false)
        interactor.findSong(name, ITunesMetaData.INSTANCE)
    }

    override fun onSongFound(vendor: VendorMetaData, songInfo: SongInfo) {
        view?.enableLoading(false)
        view?.setMainText(songInfo.name)
        this.songInfo = songInfo
    }

    override fun onError(text: String) {
        view?.showError()
        view?.setMainText(text)
        view?.enableLoading(false)
    }
}