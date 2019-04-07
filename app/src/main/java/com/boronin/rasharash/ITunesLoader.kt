package com.boronin.rasharash

import java.net.URL

class ITunesLoader(private val url: String = "https://itunes.apple.com/search?term=") {
    fun findSongUrl(songName: String): String {
        val request = url + songName.replace(" ", "+")
        val url = URL(request)
        val html = url.readText()
        return html.substring(getStartIndex(html), getEndIndex(html))
    }

    private fun getStartIndex(html: String): Int {
        return html.indexOf("trackViewUrl\":\"") + 15
    }

    private fun getEndIndex(html: String): Int {
        return html.indexOf("previewUrl") - 5
    }
}