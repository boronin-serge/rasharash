package com.boronin.rasharash.models

import com.boronin.rasharash.models.vendor.VendorMetaData

data class SearchResult(val songInfo: SongInfo, val vendorMetaData: VendorMetaData)