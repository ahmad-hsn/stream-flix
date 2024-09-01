package com.medialibrary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KnownFor(
    @SerialName("backdrop_path")
    var backdropPath: String? = null,

    var id: Int,

    var title: String? = null,

    @SerialName("original_title")
    var originalTitle: String? = null,

    var overview: String? = null,

    @SerialName("poster_path")
    var posterPath: String? = null,

    @SerialName("media_type")
    var mediaType: String? = null,

    var adult: Boolean? = null,

    @SerialName("original_language")
    var originalLanguage: String? = null,

    @SerialName("genre_ids")
    var genreIds: List<Int>? = null,

    var popularity: Double? = null,

    @SerialName("release_date")
    var releaseDate: String? = null,

    var video: Boolean? = null,

    @SerialName("vote_average")
    var voteAverage: Double? = null,

    @SerialName("vote_count")
    var voteCount: Int? = null
)
