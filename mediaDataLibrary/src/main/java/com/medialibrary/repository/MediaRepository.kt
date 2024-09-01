package com.medialibrary.repository

import com.medialibrary.network.SafeApiCall
import com.medialibrary.service.IApiService
import javax.inject.Inject


class MediaRepository @Inject constructor(private val apiService: IApiService): SafeApiCall {
    suspend fun getMedia(query: String) = safeApiCall {
        val result = apiService.getMedia(query)
        result
    }
}