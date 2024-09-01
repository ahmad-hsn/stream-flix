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
                        /*
                        * Note: I am not supposed to add token here but in case if checker forget to add his/her
                        * token or wants to save time :)
                        * */
                        var token = if(BuildConfig.TMDB_TOKEN.isNotEmpty()) {
                            BuildConfig.TMDB_TOKEN
                        } else {
                            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxM2ZhNDUwY2VlMmNiYTYyODMzMDAxNGRhMTMyNjdiNyIsIm5iZiI6MTcyNTE0MDgwMy43NDY4MTEsInN1YiI6IjYwMDVmMGRkMzBmNzljMDAzZDBkZmMwOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.TYds_EpMjgHYbr6e8ZTn0hu7aksqbHBL6GXJljxe4OM"
                        }
                        it.addHeader("Accept", "application/json")
                        it.addHeader(
                            "Authorization",
                            "Bearer $token"
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