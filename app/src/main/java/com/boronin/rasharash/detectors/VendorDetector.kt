package com.boronin.rasharash.detectors

import com.boronin.rasharash.models.MusicService

class VendorDetector private constructor() {

    fun detect(url: String?): MusicService {
        if (url.isNullOrEmpty()) return MusicService.NONE

        for (service in MusicService.values()) {
            if (service.containsUrl(url)) {
                return service
            }
        }

        return MusicService.NONE
    }

    companion object {
        val INSTANCE = VendorDetector()
    }
}