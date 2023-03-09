package com.xebiaassignment.domain.use_cases


import com.xebiaassignment.data.mappers.popularMovies
import com.xebiaassignment.data.utils.Resource
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.domain.model.PopularMoviesData
import com.xebiaassignment.domain.repo.PopularMoviesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCasePopularMovies @Inject constructor(
    private val popularMoviesRepo: PopularMoviesRepo
) {
    operator fun invoke(
        apiKey: String,
        lang: String,
        page: Int
    ): Flow<Resource<List<PopularMoviesData>>> = flow {

        when (val result = popularMoviesRepo.getPopularMovies(apiKey, lang, page)) {
            is ResultWrapper.Success -> {
                val list = if (result.data.results.isNullOrEmpty())
                    emptyList()
                else
                    result.data.results.map { it.popularMovies() }
                emit(Resource.Success(data = list))
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