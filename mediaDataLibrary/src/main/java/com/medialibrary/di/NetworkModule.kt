package com.medialibrary.di

import android.content.Context
import com.medialibrary.service.IApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideApiService(
        @ApplicationContext context: Context, remoteDataSource: RemoteDataSource
    ): IApiService {
        return remoteDataSource.buildApi(IApiService::class.java, context)
    }
}