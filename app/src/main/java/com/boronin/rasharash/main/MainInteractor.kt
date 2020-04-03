package com.boronin.rasharash.main

import com.boronin.rasharash.base.BaseInteractor
import com.boronin.rasharash.detectors.SongNameDetector
import com.boronin.rasharash.detectors.SongUrlDetector
import com.boronin.rasharash.detectors.VendorDetector
import com.boronin.rasharash.models.vendor.VendorMetaData
import com.boronin.rasharash.utils.schedulers
import io.reactivex.Observable

class MainInteractor : MainContract.Interactor, BaseInteractor() {

    override fun detectSongName(songUrl: String?): Observable<String> {
        val metaData = VendorDetector.INSTANCE.detect(songUrl)
            .getMetaData().also {
                it.sourceUrl = songUrl ?: ""
            }

        return SongNameDetector(metaData).getSongName().schedulers()
    }

    override fun findSong(songName: String, vendor: VendorMetaData) =
        SongUrlDetector(songName, vendor).getSongUrl().schedulers()
}