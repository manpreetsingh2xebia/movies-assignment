package com.xebiaassignment.domain.use_cases


import com.xebiaassignment.data.mappers.movieDetail
import com.xebiaassignment.data.utils.Resource
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.domain.model.MovieDetailData
import com.xebiaassignment.domain.repo.MovieDetailRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseMovieDetail @Inject constructor(
    private val movieDetailRepo: MovieDetailRepo
) {
    operator fun invoke(
        movieId: Int
    ): Flow<Resource<MovieDetailData>> = flow {
        when (val result = movieDetailRepo.getMovieDetail(movieId = movieId)) {
            is ResultWrapper.Success -> {
                emit(Resource.Success(data = result.data.movieDetail()))
            }
            is ResultWrapper.GenericError -> {
                emit(Resource.Error(message = result.message ?: ""))
            }
            is ResultWrapper.NetworkError -> {
                emit(Resource.Error(message = result.message))
            }
        }
    }
}