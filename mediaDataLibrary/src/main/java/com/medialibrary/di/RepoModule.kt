package com.medialibrary.di

import com.medialibrary.repository.MediaRepository
import com.medialibrary.service.IApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun provideDataRepository(
        apiService: IApiService
    ): MediaRepository {
        return MediaRepository(apiService)
    }
}