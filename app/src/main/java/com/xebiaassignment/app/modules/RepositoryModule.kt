package com.xebiaassignment.app.modules

import com.xebiaassignment.data.data_source.ApiService
import com.xebiaassignment.data.repo.NowPlayingMovieImpl
import com.xebiaassignment.domain.repo.NowPlayingRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideNowPlayingRepo(apiService: ApiService) : NowPlayingRepo {
        return NowPlayingMovieImpl(apiService)
    }
}