package com.xebiaassignment.domain.use_cases

import com.google.common.truth.Truth.assertThat
import com.xebiaassignment.data.model.MovieItem
import com.xebiaassignment.data.model.MovieListResponse
import com.xebiaassignment.data.utils.Resource
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.domain.model.NowPlayingData
import com.xebiaassignment.domain.repo.NowPlayingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UseCaseNwPlayingTest {

    val listOfMovieList = listOf<MovieItem>(
        MovieItem(
            id = 1,
            posterPath = "image.png",
            originalTitle = "Movie 1",
            releaseDate = "23-05-2021",
            voteAverage = 7.5,
            voteCount = 4578,
        ),
        MovieItem(
            id = 2,
            posterPath = "image2.png",
            originalTitle = "Movie 3",
            releaseDate = "23-05-2022",
            voteAverage = 8.5,
            voteCount = 6398,
        )
    )

    val expectedNowPlaying = listOf<NowPlayingData>(
        NowPlayingData(
            id = 1,
            imagePath = "image.png",
        ),
        NowPlayingData(
            id = 2,
            imagePath = "image2.png",
        )
    )


    private var testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var useCaseNwPlaying: UseCaseNwPlaying

    @Mock
    lateinit var nowPlayingRepo: NowPlayingRepo

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }


    @Test
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_if_useCase_return_success_mapping() = runTest {

        Mockito.`when`(nowPlayingRepo.getNowPlayingMovies("1223", "us_Eng", 1))
            .thenReturn(
                ResultWrapper.Success(
                    MovieListResponse(
                        totalResults = 2,
                        results = listOfMovieList
                    )
                )
            )
        useCaseNwPlaying = UseCaseNwPlaying(nowPlayingRepo = nowPlayingRepo)

        testDispatcher.scheduler.advanceUntilIdle()

        val result = useCaseNwPlaying("1223", "us_Eng", 1).first()
        assertThat((result as Resource.Success).data.size).isEqualTo(2)
        assertThat(result.data).isEqualTo(expectedNowPlaying)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun test_if_useCase_return_Error() = runTest {

        Mockito.`when`(nowPlayingRepo.getNowPlayingMovies("1223", "us_Eng", 1))
            .thenReturn(
                ResultWrapper.GenericError(
                    code = 401,
                    message = "Invalid Id"
                )
            )
        useCaseNwPlaying = UseCaseNwPlaying(nowPlayingRepo = nowPlayingRepo)

        val result = useCaseNwPlaying("1223", "us_Eng", 1).first()
        assertThat((result as Resource.Error).message).isEqualTo("Invalid Id")
    }
}