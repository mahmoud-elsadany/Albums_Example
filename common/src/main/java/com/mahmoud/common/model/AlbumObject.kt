package com.mahmoud.common.model

import androidx.annotation.Keep

@Keep
data class AlbumObject(
    val name: String,
    val artist: String,
    val image: String,
    val genre: String,
    val releaseDate: String,
    val copyright: String,
    val iTunesLink: String
)
