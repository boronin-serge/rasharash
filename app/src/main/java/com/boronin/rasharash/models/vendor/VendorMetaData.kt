package com.boronin.rasharash.models.vendor

import android.graphics.Color
import com.boronin.rasharash.models.song.SongInfo
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

abstract class VendorMetaData {
    private val gson = Gson()

    var title: String = ""
    var sourceUrl: String = ""
    var color: Int = Color.WHITE
    var searchApiUrl: String = ""

    abstract fun getStartNameIndex(html: String): Int

    abstract fun getEndNameIndex(html: String): Int

    abstract fun getSearchResult(response: JSONObject): List<SongInfo>?

    abstract fun getSearchSongUrl(songName: String): String


    // region private

    protected fun parseJsonToModel(jsonArray: JSONArray): List<SongInfo> {
        val list = mutableListOf<SongInfo>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val item = gson.fromJson<SongInfo>(jsonObject.toString(), SongInfo::class.java)
            item.vendorMetaData = this
            list.add(item)
        }
        return list
    }

    // endregion
}