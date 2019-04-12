package com.boronin.rasharash

import com.boronin.rasharash.vendor.VendorMetaData
import java.net.URL

class SongFinder(private val vendorMetaData: VendorMetaData) {
    fun findSongUrl(songName: String): String {
        val request = vendorMetaData.searchApiUrl + songName.replace(" ", "+")
        val url = URL(request)
        val html = url.readText()
        return html.substring(vendorMetaData.getStartUrlIndex(html), vendorMetaData.getEndUrlIndex(html))
    }
}