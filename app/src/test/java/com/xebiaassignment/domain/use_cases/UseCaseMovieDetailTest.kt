package com.xebiaassignment.domain.use_cases

import com.google.common.truth.Truth
import com.xebiaassignment.data.model.GenresItem
import com.xebiaassignment.data.model.MovieDetailResponse
import com.xebiaassignment.data.model.MovieListResponse
import com.xebiaassignment.data.utils.Resource
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.domain.model.MovieDetailData
import com.xebiaassignment.domain.repo.MovieDetailRepo
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


class UseCaseMovieDetailTest{

    val movieDetailResponse = MovieDetailResponse(
        id = 1234,
        posterPath = "image.png",
        originalTitle = "Movie 1",
        releaseDate = "22-01-2020",
        overview = "overview movie detail",
        runtime = 1,
        tagline = "tag line",
        homepage= "https://www.knockatthecabin.com",
        imdbId= "tt15679400",
        status= "Released",
        video= false,
        popularity= 3422.537,
        revenue= 52000000,
        voteAverage= 6.457,
        voteCount= 444,
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

    private var testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var useCaseMovieDetail : UseCaseMovieDetail

    @Mock
    lateinit var movieDetailRepo: MovieDetailRepo

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_if_useCase_return_success_mapping() = runTest {

        Mockito.`when`(movieDetailRepo.getMovieDetail(1234))
            .thenReturn(
                ResultWrapper.Success(
                    movieDetailResponse
                )
            )
        useCaseMovieDetail = UseCaseMovieDetail(movieDetailRepo)
        val result = useCaseMovieDetail(1234).first()
        Truth.assertThat((result as Resource.Success).data).isEqualTo(expectedResponse)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun test_if_useCase_return_Error() = runTest {

        Mockito.`when`(movieDetailRepo.getMovieDetail(1234))
            .thenReturn(
                ResultWrapper.GenericError(
                    code = 401,
                    message = "Invalid Id"
                )
            )
        useCaseMovieDetail = UseCaseMovieDetail(movieDetailRepo)
        val result = useCaseMovieDetail(1234).first()
        Truth.assertThat((result as Resource.Error).message).isEqualTo("Invalid Id")
    }


    @Test
    fun tearDown() {
        Dispatchers.resetMain()
    }
}