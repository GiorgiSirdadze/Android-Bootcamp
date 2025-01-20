package com.example.revisions

import com.squareup.moshi.Json

enum class LastMessageType {
    @Json(name = "text") TEXT,
    @Json(name = "file") FILE,
    @Json(name = "voice") VOICE
}
