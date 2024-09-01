package com.medialibrary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaResponse(
    @SerialName("page")
    var page: Int? = null,

    @SerialName("total_pages")
    var totalPages: Int? = null,

    @SerialName("total_results")
    var totalResults: Int? = null,

    @SerialName("results")
    var results: ArrayList<MediaData> = arrayListOf(),
) {

    fun categorizeResults(results: List<MediaData>): Map<String, List<MediaData>> {
        return results.groupBy {
            it.mediaType ?: ""
        }
    }
}