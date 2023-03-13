package com.xebiaassignment.data.repo

import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.doAnswer
import com.xebiaassignment.data.data_source.ApiService
import com.xebiaassignment.data.model.GenresItem
import com.xebiaassignment.data.model.MovieDetailResponse
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.domain.model.MovieDetailData
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

class MovieDetailRepoTest {
    val movieDetailResponse = MovieDetailResponse(
        id = 1234,
        posterPath = "image.png",
        originalTitle = "Movie 1",
        releaseDate = "22-01-2020",
        overview = "overview movie detail",
        runtime = 1,
        tagline = "tag line",
        homepage = "https://www.knockatthecabin.com",
        imdbId = "tt15679400",
        status = "Released",
        video = false,
        popularity = 3422.537,
        revenue = 52000000,
        voteAverage = 6.457,
        voteCount = 444,
        genres = listOf(
            GenresItem(
                id = 1,
                name = "Animation",
            ),
            GenresItem(
                id = 2,
                name = "Comedy",
            )
        )
    )

    val expectedResponse = MovieDetailData(
        imagePath = "image.png",
        originalTitle = "Movie 1",
        releaseDate = "22-01-2020",
        overview = "overview movie detail",
        genres = listOf(
            GenresItem(
                id = 1,
                name = "Animation",
            ),
            GenresItem(
                id = 2,
                name = "Comedy",
            )
        )
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
            apiService.movieDetail(1234)
        ).thenReturn(movieDetailResponse)
        testDispatcher.scheduler.advanceUntilIdle()

        val repo = MovieDetailRepoImpl(apiService, testDispatcher)
        val result = repo.getMovieDetail(1234)

        Truth.assertThat(result is ResultWrapper.Success).isEqualTo(true)
        Truth.assertThat((result as ResultWrapper.Success).data).isEqualTo(expectedResponse)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_if_nowPlaying_sendGenericError() = runTest {

        Mockito.`when`(
            apiService.movieDetail(1234)
        ).thenThrow(HttpException::class.java)

        testDispatcher.scheduler.advanceUntilIdle()

        val repo = MovieDetailRepoImpl(apiService, testDispatcher)
        val result = repo.getMovieDetail(1234)

        Truth.assertThat(result is ResultWrapper.GenericError).isEqualTo(true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_if_nowPlaying_sendIOException_UnknownHostException() = runTest {

        Mockito.`when`(
            apiService.movieDetail(1234)
        ).doAnswer { throw UnknownHostException() }

        testDispatcher.scheduler.advanceUntilIdle()

        val repo = MovieDetailRepoImpl(apiService, testDispatcher)
        val result = repo.getMovieDetail(1234)

        Truth.assertThat(result is ResultWrapper.NetworkError).isEqualTo(true)
        Truth.assertThat((result as ResultWrapper.NetworkError).message)
            .isEqualTo("Please check your internet connection")
    }

}