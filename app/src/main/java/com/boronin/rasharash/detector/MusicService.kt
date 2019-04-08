package com.boronin.rasharash.detector

enum class MusicService {
    YANDEX {
        override fun isDetected(url: String): Boolean {
            return url.contains("music.yandex.ru")
        }

    },
    ITUNES {
        override fun isDetected(url: String): Boolean {
            return url.contains("itunes.apple.com")
        }
    },
    NONE {
        override fun isDetected(url: String): Boolean {
            return true
        }

    };

    abstract fun isDetected(url: String): Boolean
}