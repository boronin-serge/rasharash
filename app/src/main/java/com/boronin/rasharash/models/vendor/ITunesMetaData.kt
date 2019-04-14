package com.boronin.rasharash.models.vendor

import org.json.JSONObject

class ITunesMetaData private constructor(): VendorMetaData() {

    override fun getStartNameIndex(html: String): Int {
        return 0
    }

    override fun getEndNameIndex(html: String): Int {
        return 0
    }

    override fun getTrackUrl(response: JSONObject): String {
        return response.getJSONArray("results").getJSONObject(0).getString("trackViewUrl")
    }

    override fun getSearchSongUrl(songName: String): String {
        return searchApiUrl + songName.replace(" ", "+")
    }

    companion object {
        val INSTANCE = ITunesMetaData().apply {
            title = "iTunes"
            searchApiUrl = "https://itunes.apple.com/search?term="
        }
    }
}