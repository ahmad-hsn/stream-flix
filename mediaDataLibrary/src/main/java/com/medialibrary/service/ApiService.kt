package com.medialibrary.service

import com.medialibrary.model.MediaResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface IApiService {
    @GET("search/multi")
    suspend fun getMedia(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 2
    ): MediaResponse
}