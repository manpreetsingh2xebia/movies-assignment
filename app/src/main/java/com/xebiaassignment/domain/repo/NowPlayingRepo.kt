package com.xebiaassignment.domain.repo

import com.xebiaassignment.data.model.MovieListResponse
import com.xebiaassignment.data.utils.ResultWrapper

interface NowPlayingRepo {
    suspend fun getNowPlayingMovies(
        apiKey : String,
        lang : String,
        page : Int,
    ) : ResultWrapper<MovieListResponse>
}