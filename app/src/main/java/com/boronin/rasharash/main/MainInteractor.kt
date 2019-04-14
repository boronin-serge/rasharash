package com.boronin.rasharash.main

import com.boronin.rasharash.detectors.SongNameDetector
import com.boronin.rasharash.detectors.SongUrlDetector
import com.boronin.rasharash.base.BaseInteractor
import com.boronin.rasharash.models.MusicService
import com.boronin.rasharash.detectors.VendorDetector
import com.boronin.rasharash.models.vendor.ITunesMetaData
import com.boronin.rasharash.models.vendor.VendorMetaData
import com.boronin.rasharash.models.vendor.YandexMetaData
import com.boronin.rasharash.utils.Constants
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
                    callback.onError(Constants.TRACK_NOT_RECOGNIZED)
                })
        }
        else {
            callback.onError(Constants.LOADING_ERROR)
        }
    }

    override fun findSong(songName: String, vendor: VendorMetaData) {
        val task = SongUrlDetector(songName, vendor).getSongUrl()
        task.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSongFound(vendor, it)
            }, {
                callback.onError(Constants.LOADING_ERROR)
            })
    }
}