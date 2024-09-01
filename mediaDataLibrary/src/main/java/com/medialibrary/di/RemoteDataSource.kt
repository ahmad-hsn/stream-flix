package com.medialibrary.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.medialibrary.BuildConfig
import com.medialibrary.network.NoInternetException
import isInternetAvailable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {
    val json = Json {
        ignoreUnknownKeys = true
    }

    fun <IApiService> buildApi(
        api: Class<IApiService>,
        context: Context,
    ): IApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(getRetrofitClient(context))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(api)
    }

    private fun getRetrofitClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    if (!isInternetAvailable(context)) {
                        throw NoInternetException("Make sure you have an active data connection")
                    } else {
                        it.addHeader("Accept", "application/json")
                        it.addHeader(
                            "Authorization",
                            "Bearer ${BuildConfig.TMDB_TOKEN}"
                        )
                    }
                }.build())
            }.also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
                client.connectTimeout(10, TimeUnit.SECONDS)
                client.readTimeout(10, TimeUnit.SECONDS)
            }.build()
    }
}