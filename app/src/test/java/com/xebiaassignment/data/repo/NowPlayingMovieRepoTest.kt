package com.xebiaassignment.data.repo

import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.doAnswer
import com.xebiaassignment.data.data_source.ApiService
import com.xebiaassignment.data.model.MovieItem
import com.xebiaassignment.data.model.MovieListResponse
import com.xebiaassignment.data.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import java.net.UnknownHostException

class NowPlayingMovieRepoTest {

    private val resultItems = listOf(
        MovieItem(
            overview = "movie 1"
        ),
        MovieItem(
            overview = "movie 2"
        )
    )
    private val responseMovies = MovieListResponse(
        results = resultItems
    )

    @ExperimentalCoroutinesApi
    private var testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var apiService: ApiService

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun test_if_nowPlaying_sendSuccess() = runTest {

        Mockito.`when`(
            apiService.nowPlaying("api_key", "", 1)
        ).thenReturn(responseMovies)
        testDispatcher.scheduler.advanceUntilIdle()

        val repo = NowPlayingMovieImpl(apiService, testDispatcher)
        val result = repo.getNowPlayingMovies("api_key", "", 1)

        Truth.assertThat(result is ResultWrapper.Success).isEqualTo(true)
        Truth.assertThat((result as ResultWrapper.Success).data.results!!.size).isEqualTo(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_if_nowPlaying_sendGenericError() = runTest {

        Mockito.`when`(
            apiService.nowPlaying("api_key", "", 1)
        ).thenThrow(HttpException::class.java)

        testDispatcher.scheduler.advanceUntilIdle()

        val repo = NowPlayingMovieImpl(apiService, testDispatcher)

        val result = repo.getNowPlayingMovies("api_key", "", 1)

        Truth.assertThat(result is ResultWrapper.GenericError).isEqualTo(true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_if_nowPlaying_sendIOException_UnknownHostException() = runTest {

        Mockito.`when`(
            apiService.nowPlaying("api_key", "", 1)
        ).doAnswer { throw UnknownHostException() }

        testDispatcher.scheduler.advanceUntilIdle()

        val repo = NowPlayingMovieImpl(apiService, testDispatcher)

        val result = repo.getNowPlayingMovies("api_key", "", 1)

        Truth.assertThat(result is ResultWrapper.NetworkError).isEqualTo(true)
        Truth.assertThat( (result as ResultWrapper.NetworkError).message).isEqualTo("Please check your internet connection")
    }

}