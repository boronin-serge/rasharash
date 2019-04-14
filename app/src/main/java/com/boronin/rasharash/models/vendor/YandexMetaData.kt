package com.boronin.rasharash.models.vendor

import org.json.JSONObject

class YandexMetaData private constructor(): VendorMetaData() {

    override fun getStartNameIndex(html: String): Int {
        return html.indexOf("<title>") + 7
    }

    override fun getEndNameIndex(html: String): Int {
        return html.indexOf(". Слушать онлайн")
    }

    override fun getTrackUrl(response: JSONObject): String {
        return ""
    }

    override fun getSearchSongUrl(songName: String): String {
        return ""
    }

    companion object {
        val INSTANCE = YandexMetaData().apply {

        }
    }
}