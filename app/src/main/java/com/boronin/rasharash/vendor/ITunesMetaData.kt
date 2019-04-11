package com.boronin.rasharash.vendor

class ITunesMetaData private constructor(): VendorMetaData() {
    override fun getStartUrlIndex(html: String): Int {
        return html.indexOf(startSearchPattern) + startSearchPattern.length
    }

    override fun getEndUrlIndex(html: String): Int {
        return html.indexOf(endSearchPattern) - 5
    }

    override fun getStartNameIndex(html: String): Int {
        return 0
    }

    override fun getEndNameIndex(html: String): Int {
        return 0
    }

    companion object {
        val INSTANCE = ITunesMetaData().apply {
            url = "https://itunes.apple.com/search?term="
            startSearchPattern = "trackViewUrl\":\""
            endSearchPattern = "previewUrl"
        }
    }
}