package com.boronin.rasharash.detector

class SourceDetector private constructor() {

    fun detect(url: String): MusicService {
        for (service in MusicService.values()) {
            if (service.containsUrl(url)) {
                return service
            }
        }

        return MusicService.NONE
    }

    companion object {
        val INSTANCE = SourceDetector()
    }
}