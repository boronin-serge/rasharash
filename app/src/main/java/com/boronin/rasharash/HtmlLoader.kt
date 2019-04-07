package com.boronin.rasharash

import io.reactivex.Single
import java.net.URL

class HtmlLoader(private val url: String?) {
    fun getSongName(): Single<SongInfo> {
        return Single.create{ emitter ->
            val url = URL(url)
            val html = url.readText()
            val songName = html.substring(getStartIndex(html), getEndIndex(html))
            val songUrl = ITunesLoader().findSongUrl(songName)
            val songInfo = SongInfo(songName).apply {
                itunesUrl = songUrl
            }
            emitter.onSuccess(songInfo)
        }
    }

    private fun getStartIndex(html: String): Int {
        return html.indexOf("<title>") + 7
    }

    private fun getEndIndex(html: String): Int {
        return html.indexOf(". Слушать онлайн")
    }
}