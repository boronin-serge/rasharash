package com.boronin.rasharash.main

import com.boronin.rasharash.HtmlLoader
import com.boronin.rasharash.SongInfo
import com.boronin.rasharash.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MainPresenter: BasePresenter<MainContract.View>(), MainContract.Presenter  {
    private var inputUrl: String? = null
    private var songInfo: SongInfo? = null

    override fun viewIsReady() {
        with(view!!) {
            initUI()
            readInputUrl()
            setMainText(inputUrl ?: "")
            enableLoading(true)
        }
    }

    override fun onUpdateInput(input: String?) {
        inputUrl = input
        onSearchSongName()
    }

    override fun onSearchSongName() {
        val task = HtmlLoader(inputUrl).getSongName()
        task.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ foundedSong ->
                foundedSong?.let {
                    view?.setMainText(it.name)
                }
                songInfo = foundedSong
                view?.enableLoading(false)
            }, {
                view?.showError()
                view?.setMainText("Ошибка загрузки данных")
                view?.enableLoading(false)
            })
    }

    override fun onSearchItunesSong() {

    }

    override fun onShare() {
        try {
            view?.shareLink(songInfo?.itunesUrl!!)
        } catch (e: Exception) {
            view?.showError()
        }
    }
}