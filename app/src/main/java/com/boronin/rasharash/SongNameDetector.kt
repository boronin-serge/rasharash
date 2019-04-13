package com.boronin.rasharash

import com.boronin.rasharash.vendor.VendorMetaData
import io.reactivex.Observable
import java.net.URL

class SongNameDetector(private val source: VendorMetaData) {
    fun getSongName(): Observable<String> {
        return Observable.fromCallable {
            val url = URL(source.sourceUrl)
            val html = url.readText()
            val songName = html.substring(source.getStartNameIndex(html), source.getEndNameIndex(html))
            songName
        }
    }
}
