package com.mahmoud.common.model

import androidx.annotation.Keep

@Keep
data class AlbumsResponse(
    val feed: Feed
)

@Keep
data class Feed(
    val copyright: String,
    val results: List<Result>
)

@Keep
data class Result(
    val artistId: String,
    val artistName: String,
    val artistUrl: String,
    val artworkUrl100: String,
    val contentAdvisoryRating: String,
    val genres: List<Genre>,
    val id: String,
    val kind: String,
    val name: String,
    val releaseDate: String,
    val url: String
)

@Keep
data class Genre(
    val genreId: String,
    val name: String,
    val url: String
)