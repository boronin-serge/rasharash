package com.boronin.rasharash

import com.boronin.rasharash.vendor.VendorMetaData
import io.reactivex.Observable

class SongUrlDetector(private val songName: String, private val target: VendorMetaData) {
    fun getSongUrl(): Observable<SongInfo> {
        return Observable.fromCallable{
            val songUrl = SongFinder(target).findSongUrl(songName)
            SongInfo(songName, songUrl)
        }
    }
}