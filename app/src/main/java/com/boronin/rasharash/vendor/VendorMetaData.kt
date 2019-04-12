package com.boronin.rasharash.vendor

abstract class VendorMetaData {
    var sourceUrl: String = ""
    var searchApiUrl: String = ""
    var startSearchPattern: String = ""
    var endSearchPattern: String = ""

    // TODO: rename functions
    abstract fun getStartUrlIndex(html: String): Int

    abstract fun getEndUrlIndex(html: String): Int

    abstract fun getStartNameIndex(html: String): Int

    abstract fun getEndNameIndex(html: String): Int
}