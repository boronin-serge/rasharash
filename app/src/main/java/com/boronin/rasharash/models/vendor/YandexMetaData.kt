package com.boronin.rasharash.models.vendor

import com.boronin.rasharash.models.song.SongInfo
import org.json.JSONObject

class YandexMetaData private constructor(): VendorMetaData() {

    override fun getStartNameIndex(html: String): Int {
        return html.indexOf("<title>") + 7
    }

    override fun getEndNameIndex(html: String): Int {
        return html.indexOf(". Слушать онлайн")
    }

    override fun getSearchResult(response: JSONObject): List<SongInfo>? {
        return null
    }

    override fun getSearchSongUrl(songName: String): String {
        return ""
    }

    companion object {
        val INSTANCE = YandexMetaData().apply {

        }
    }
}