package com.xebiaassignment.domain.use_cases


import com.xebiaassignment.data.mappers.nowPlaying
import com.xebiaassignment.data.utils.Resource
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.domain.model.NowPlayingData
import com.xebiaassignment.domain.repo.NowPlayingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseNwPlaying @Inject constructor(
    private val nowPlayingRepo: NowPlayingRepo
) {
    operator fun invoke(
        apiKey : String,
        lang : String,
        page: Int
    ) : Flow<Resource<List<NowPlayingData>>> = flow{
        val result = nowPlayingRepo.getNowPlayingMovies(apiKey,lang,page)
        emit(Resource.Loading)
        when(result){
            is ResultWrapper.Success ->{
                val list = if(result.data.results.isNullOrEmpty())
                    emptyList()
                else
                    result.data.results.map { it.nowPlaying()}
                emit(Resource.Success(data = list))
            }
            is ResultWrapper.GenericError ->{
                emit(Resource.Error(message = result.message?:""))
            }
            is ResultWrapper.NetworkError ->{
                emit(Resource.Error(message = result.message?:""))
            }
        }
    }
}