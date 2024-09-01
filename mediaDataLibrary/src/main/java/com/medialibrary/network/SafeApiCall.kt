package com.medialibrary.network

import android.accounts.NetworkErrorException
import android.util.Log
import com.medialibrary.model.GeneralError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.e("SafeApiCall", "safeApiCall: ${throwable.message}")
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            404,
                            GeneralError(success = false, message = "Something Went Wrong"),
                            null
                        )
                    }

                    is NetworkErrorException -> {
                        Resource.Failure(true, null, null)
                    }

                    else -> {
                        Resource.Failure(false, throwable.hashCode(), null, throwable.message)
                    }
                }
            }
        }
    }
}