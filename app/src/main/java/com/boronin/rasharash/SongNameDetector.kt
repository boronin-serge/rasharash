package com.boronin.rasharash

import com.boronin.rasharash.vendor.VendorMetaData
import io.reactivex.Single
import java.net.URL

class SongNameDetector(private val source: VendorMetaData) {
    fun getSongName(): Single<String> {
        return Single.create{ emitter ->
            val url = URL(source.sourceUrl)
            val html = url.readText()
            val songName = html.substring(source.getStartNameIndex(html), source.getEndNameIndex(html))
            emitter.onSuccess(songName)
        }
    }
}
