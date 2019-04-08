package com.boronin.rasharash.detector

class SourceDetector {

    fun detect(url: String): MusicService {
        return when {
            MusicService.YANDEX.isDetected(url) -> MusicService.YANDEX
            MusicService.ITUNES.isDetected(url) -> MusicService.ITUNES
            else -> MusicService.NONE
        }
    }

    companion object {
        val INSTANCE = SourceDetector()
    }
}