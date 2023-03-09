package com.xebiaassignment.data.repo

import com.xebiaassignment.data.data_source.ApiService
import com.xebiaassignment.data.model.MovieDetailResponse
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.data.utils.safeApiCall
import com.xebiaassignment.domain.repo.MovieDetailRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MovieDetailRepoImpl (
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MovieDetailRepo {

    override suspend fun getMovieDetail(
        movieId: Int
    ): ResultWrapper<MovieDetailResponse> {
        return safeApiCall(dispatcher){
            apiService.movieDetail(movieId)
        }
    }

}