package com.boronin.rasharash.models

enum class MusicService {
    YANDEX {
        override fun containsUrl(url: String): Boolean {
            return url.contains("music.yandex.ru")
        }
    },
    ITUNES {
        override fun containsUrl(url: String): Boolean {
            return url.contains("itunes.apple.com")
        }
    },
    NONE {
        override fun containsUrl(url: String): Boolean {
            return true
        }
    };

    abstract fun containsUrl(url: String): Boolean
}