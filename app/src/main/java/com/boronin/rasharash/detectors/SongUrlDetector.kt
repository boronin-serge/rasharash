package com.boronin.rasharash.detectors

import com.boronin.rasharash.models.SongInfo
import com.boronin.rasharash.models.vendor.VendorMetaData
import com.boronin.rasharash.utils.HtmlParser
import io.reactivex.Observable
import java.net.URL

class SongUrlDetector(private val songName: String, private val target: VendorMetaData) {
    fun getSongUrl(): Observable<SongInfo> {
        return Observable.fromCallable{
            val request = target.getSearchSongUrl(songName)
            val url = URL(request)
            val html = url.readText()
            val json = HtmlParser.INSTANCE.getJsonFromHtml(html)
            val songUrl = target.getTrackUrl(json)
            SongInfo(songName, songUrl)
        }
    }
}