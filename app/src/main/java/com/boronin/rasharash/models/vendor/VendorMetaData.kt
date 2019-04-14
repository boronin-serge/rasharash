package com.boronin.rasharash.models.vendor

import org.json.JSONObject

abstract class VendorMetaData {
    var sourceUrl: String = ""
    var searchApiUrl: String = ""

    abstract fun getStartNameIndex(html: String): Int

    abstract fun getEndNameIndex(html: String): Int

    abstract fun getTrackUrl(response: JSONObject): String

    abstract fun getSearchSongUrl(songName: String): String
}