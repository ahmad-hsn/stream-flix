package com.medialibrary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MediaData(
    @SerialName("backdrop_path")
    var backdropPath: String? = null,

    @SerialName("id")
    var id: Int? = null,

    @SerialName("name")
    var name: String? = null,

    @SerialName("original_name")
    var originalName: String? = null,

    @SerialName("overview")
    var overview: String? = null,

    @SerialName("poster_path")
    var posterPath: String? = null,

    @SerialName("media_type")
    var mediaType: String? = null,

    @SerialName("adult")
    var adult: Boolean? = null,

    @SerialName("gender")
    var gender: Int? = null,

    @SerialName("original_language")
    var originalLanguage: String? = null,

    @SerialName("genre_ids")
    var genreIds: ArrayList<Int>? = arrayListOf(),

    @SerialName("popularity")
    var popularity: Double? = null,

    @SerialName("first_air_date")
    var firstAirDate: String? = null,

    @SerialName("vote_average")
    var voteAverage: Double? = null,

    @SerialName("vote_count")
    var voteCount: Int? = null,

    @SerialName("origin_country")
    var originCountry: ArrayList<String>? = arrayListOf(),

    @SerialName("known_for")
    var knownFor: ArrayList<KnownFor>? = arrayListOf()
)