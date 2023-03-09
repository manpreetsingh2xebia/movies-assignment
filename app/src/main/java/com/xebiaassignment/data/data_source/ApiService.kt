package com.xebiaassignment.data.data_source



import com.xebiaassignment.BuildConfig
import com.xebiaassignment.data.model.MovieDetailResponse
import com.xebiaassignment.data.model.MovieListResponse
import com.xebiaassignment.data.utils.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConstants.POPULAR)
    suspend fun popularMovies(
        @Query("api_key")  api_key : String = BuildConfig.API_KEY,
        @Query("language")  language : String,
        @Query("page")  page : Int,
    ) : MovieListResponse

    @GET(ApiConstants.NOW_PLAYING)
    suspend fun nowPlaying(
        @Query("api_key")  api_key : String = BuildConfig.API_KEY,
        @Query("language")  language : String,
        @Query("page")  page : Int,
    ) : MovieListResponse

    @GET(ApiConstants.MOVIE_DETAIL)
    suspend fun movieDetail(
        @Path("movie_id") movieId : String,
        @Query("api_key")  api_key : String = BuildConfig.API_KEY,
        @Query("language")  language : String
    ) : Response<MovieDetailResponse>
}