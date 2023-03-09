package com.xebiaassignment.data.repo

import com.xebiaassignment.data.data_source.ApiService
import com.xebiaassignment.data.model.MovieListResponse
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.data.utils.safeApiCall
import com.xebiaassignment.domain.repo.NowPlayingRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NowPlayingMovieImpl (
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : NowPlayingRepo {
    override suspend fun getNowPlayingMovies(
        apiKey: String,
        lang: String,
        page: Int
    ): ResultWrapper<MovieListResponse> {
        return safeApiCall(dispatcher){
            apiService.nowPlaying(
                apiKey,
                lang,
                page
            )
        }
    }
}