package com.xebiaassignment.domain.repo

import com.xebiaassignment.data.model.MovieDetailResponse
import com.xebiaassignment.data.utils.ResultWrapper

interface MovieDetailRepo {
    suspend fun getMovieDetail(
        movieId: Int,
    ): ResultWrapper<MovieDetailResponse>
}