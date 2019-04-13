package com.boronin.rasharash.main

import com.boronin.rasharash.SongInfo
import com.boronin.rasharash.SongNameDetector
import com.boronin.rasharash.SongUrlDetector
import com.boronin.rasharash.base.BaseInteractor
import com.boronin.rasharash.detector.MusicService
import com.boronin.rasharash.detector.VendorDetector
import com.boronin.rasharash.vendor.ITunesMetaData
import com.boronin.rasharash.vendor.VendorMetaData
import com.boronin.rasharash.vendor.YandexMetaData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainInteractor(private val callback: MainContract.InteractorCallback) : MainContract.Interactor, BaseInteractor() {
    private val vendors: HashMap<MusicService, VendorMetaData> = HashMap()

    init {
        vendors[MusicService.YANDEX] = YandexMetaData.INSTANCE
        vendors[MusicService.ITUNES] = ITunesMetaData.INSTANCE
    }

    override fun detectSongName(songUrl: String?) {
        val service: MusicService = VendorDetector.INSTANCE.detect(songUrl)

        if (vendors.containsKey(service)) {
            val metaData: VendorMetaData = vendors[service]!!
            metaData.sourceUrl = songUrl ?: ""

            val task = SongNameDetector(metaData).getSongName()
            task.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    callback.onSongNameDetected(it)
                }, {
                    callback.onError("Не удалось определить трэк")
                })
        }
        else {
            callback.onError("Ошибка загрузки данных")
        }
    }

    override fun findSong(songName: String, vendor: VendorMetaData) {
        val task = SongUrlDetector(songName, vendor).getSongUrl()
        task.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSongFound(vendor, it)
            }, {
                callback.onError("Ошибка загрузки данных")
            })
    }
}