package com.boronin.rasharash

import com.boronin.rasharash.vendor.VendorMetaData
import io.reactivex.Single
import java.net.URL

class SongNameDetector(private val source: VendorMetaData, private val target: VendorMetaData) {
    fun getSongName(): Single<SongInfo> {
        return Single.create{ emitter ->
            val url = URL(source.inputUrl)
            val html = url.readText()
            val songName = html.substring(source.getStartNameIndex(html), source.getEndNameIndex(html))
            val songUrl = SongFinder(target).findSongUrl(songName)
            val songInfo = SongInfo(songName).apply {
                this.url = songUrl
            }
            emitter.onSuccess(songInfo)
        }
    }
}