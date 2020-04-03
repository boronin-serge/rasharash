package com.boronin.rasharash.models

import com.boronin.rasharash.models.vendor.ITunesMetaData
import com.boronin.rasharash.models.vendor.VendorMetaData
import com.boronin.rasharash.models.vendor.YandexMetaData

enum class MusicService {
    YANDEX {
        override fun containsUrl(url: String): Boolean {
            return url.contains("music.yandex.ru")
        }

        override fun getMetaData() = YandexMetaData.INSTANCE
    },
    ITUNES {
        override fun containsUrl(url: String): Boolean {
            return url.contains("itunes.apple.com")
        }

        override fun getMetaData() = ITunesMetaData.INSTANCE
    },
    NONE {
        override fun containsUrl(url: String): Boolean {
            return true
        }

        override fun getMetaData(): VendorMetaData {
            throw Exception()
        }
    };

    abstract fun containsUrl(url: String): Boolean
    abstract fun getMetaData(): VendorMetaData
}