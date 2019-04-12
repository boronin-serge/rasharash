package com.boronin.rasharash.main

import com.boronin.rasharash.SongNameDetector
import com.boronin.rasharash.SongInfo
import com.boronin.rasharash.SongUrlDetector
import com.boronin.rasharash.base.BasePresenter
import com.boronin.rasharash.detector.MusicService
import com.boronin.rasharash.detector.VendorDetector
import com.boronin.rasharash.vendor.ITunesMetaData
import com.boronin.rasharash.vendor.VendorMetaData
import com.boronin.rasharash.vendor.YandexMetaData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MainPresenter: BasePresenter<MainContract.View>(), MainContract.Presenter  {
    private var inputUrl: String? = null
    private var songInfo: SongInfo? = null
    private val vendors: HashMap<MusicService, VendorMetaData> = HashMap()

    override fun viewIsReady() {
        vendors.clear()
        vendors[MusicService.YANDEX] = YandexMetaData.INSTANCE
        vendors[MusicService.ITUNES] = ITunesMetaData.INSTANCE

        with(view!!) {
            initUI()
            setMainText(inputUrl ?: "")
        }
    }

    override fun onUpdateInput(input: String?) {
        inputUrl = input ?: ""
        onSearchSongName()
    }

    override fun onSearchSongName() {
        view?.enableLoading(true)

        val service: MusicService = VendorDetector.INSTANCE.detect(inputUrl!!)
        val metaData: VendorMetaData? = vendors[service]

        metaData?.sourceUrl = inputUrl ?: ""

        if (metaData == null) {
            view?.showError()
            view?.setMainText("Ошибка загрузки данных")
            view?.enableLoading(false)
        }
        else {
            val task = SongNameDetector(metaData).getSongName()
            task.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ songName ->
                    songName?.let { onSearchItunesSong(it) }
                }, {
                    view?.showError()
                    view?.setMainText("Не удалось определить трэк")
                    view?.enableLoading(false)
                })
        }
    }

    override fun onSearchItunesSong(songName: String) {
        val task: Single<SongInfo> = SongUrlDetector(songName, ITunesMetaData.INSTANCE).getSongUrl()
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

    override fun onShare() {
        try {
            view?.shareLink(songInfo?.url!!)
        } catch (e: Exception) {
            view?.showError()
        }
    }
}