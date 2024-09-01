package com.medialibrary.network

import com.medialibrary.model.GeneralError

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Loading<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: GeneralError?,
        val throwable: String? = ""
    ) : Resource<Nothing>()
}