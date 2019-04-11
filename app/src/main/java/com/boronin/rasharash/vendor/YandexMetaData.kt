package com.boronin.rasharash.vendor

class YandexMetaData private constructor(): VendorMetaData() {
    override fun getStartUrlIndex(html: String): Int {
        return html.indexOf(startSearchPattern) + startSearchPattern.length
    }

    override fun getEndUrlIndex(html: String): Int {
        return html.indexOf(endSearchPattern) - 5
    }

    override fun getStartNameIndex(html: String): Int {
        return html.indexOf("<title>") + 7
    }

    override fun getEndNameIndex(html: String): Int {
        return html.indexOf(". Слушать онлайн")
    }

    companion object {
        val INSTANCE = YandexMetaData().apply {

        }
    }
}