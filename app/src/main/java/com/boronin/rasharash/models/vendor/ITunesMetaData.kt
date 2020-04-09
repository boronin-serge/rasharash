package com.boronin.rasharash.models.vendor

import android.graphics.Color
import com.boronin.rasharash.models.song.SongInfo
import org.json.JSONObject

class ITunesMetaData private constructor(): VendorMetaData() {

    override fun getStartNameIndex(html: String): Int {
        return 0
    }

    override fun getEndNameIndex(html: String): Int {
        return 0
    }

    override fun getSearchResult(response: JSONObject): List<SongInfo>? {
        return parseJsonToModel(response.getJSONArray("results"))
    }

    override fun getSearchSongUrl(songName: String): String {
        return searchApiUrl + songName.replace(" ", "+")
    }

    companion object {
        val INSTANCE = ITunesMetaData().apply {
            title = "iTunes"
            color = Color.parseColor("#FF265A")
            searchApiUrl = "https://itunes.apple.com/search?term="
        }
    }
}