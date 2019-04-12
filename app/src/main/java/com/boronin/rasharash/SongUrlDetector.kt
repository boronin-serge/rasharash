package com.boronin.rasharash

import com.boronin.rasharash.vendor.VendorMetaData
import io.reactivex.Single
import java.net.URL

class SongUrlDetector(private val songName: String, private val target: VendorMetaData) {
    fun getSongUrl(): Single<SongInfo> {
        return Single.create{ emitter ->
            val songUrl = SongFinder(target).findSongUrl(songName)
            val songInfo = SongInfo(songName, songUrl)
            emitter.onSuccess(songInfo)
        }
    }
}