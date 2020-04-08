package com.boronin.rasharash.models.song

import com.boronin.rasharash.models.vendor.VendorMetaData
import com.google.gson.annotations.SerializedName

data class SongInfo(
    @SerializedName("trackName") val name: String?,
    @SerializedName("trackViewUrl") val url: String?,
    @SerializedName("artistName") val artistName: String?,
    @SerializedName("artworkUrl100") val logoUrl: String?,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Int?,
    var vendorMetaData: VendorMetaData?
)