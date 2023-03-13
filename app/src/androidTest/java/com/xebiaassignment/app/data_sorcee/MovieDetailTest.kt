package com.xebiaassignment.app.data_sorcee

import android.arch.core.executor.testing.InstantTaskExecutorRule

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.xebiaassignment.BuildConfig
import com.xebiaassignment.app.helper.FileReader
import com.xebiaassignment.data.data_source.ApiService
import com.xebiaassignment.data.model.MovieDetailResponse
import com.xebiaassignment.domain.model.MovieDetailData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltAndroidTest
class MovieDetailTest {

    private var testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var mockWebServer: MockWebServer

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        hiltAndroidRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test(expected = JsonSyntaxException::class)
    fun testGet_movie_detail_ifJsonSyntaxException() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)
        testDispatcher.scheduler.advanceUntilIdle()
        val response = apiService.movieDetail(631842)
        mockWebServer.takeRequest()
        assertThat(response).isEqualTo("[]")

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGet_movie_detail_ifResultIsNotEmpty() = runTest {
        //Arrange
        val mockResponse = MockResponse()
        val resourceContent = FileReader.readFileFromResource("/response_detail.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(resourceContent)
        mockWebServer.enqueue(mockResponse)

        //Act
        testDispatcher.scheduler.advanceUntilIdle()
        val response = apiService.movieDetail(631842)
        mockWebServer.takeRequest()

        val expected = Gson().fromJson(resourceContent ,MovieDetailResponse::class.java)
        //Assertion
        assertThat(response).isEqualTo(expected)


    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGet_movie_detail_ifInternalServerError() = runTest {
        //Arrange
        val mockResponse = MockResponse()
        val resourceContent = FileReader.readFileFromResource("/response_detail_error.json")
        mockResponse.setResponseCode(501)
        mockResponse.setBody(resourceContent)
        mockWebServer.enqueue(mockResponse)
        //Act
        try {
            testDispatcher.scheduler.advanceUntilIdle()
            val response = apiService.movieDetail(631842)
            mockWebServer.takeRequest()
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    //Assertion
                    assertThat(throwable.message).isEqualTo("HTTP 501 Server Error")
                }
                else -> {}
            }
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGet_movie_detail_if_detail_for_specific_movie_not_available() = runTest {
        //Arrange
        val mockResponse = MockResponse()
        val resourceContent = FileReader.readFileFromResource("/response_detail_error.json")
        mockResponse.setResponseCode(401)
        mockResponse.setBody(resourceContent)
        mockWebServer.enqueue(mockResponse)
        //Act
        try {
            testDispatcher.scheduler.advanceUntilIdle()
            apiService.movieDetail(631842)
            mockWebServer.takeRequest()
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val error = throwable.response()?.errorBody()
                    val json = JSONObject(error!!.string())
                    val msg = json.getString("status_message")
                    //Assertion
                    assertThat(msg).isEqualTo("The resource you requested could not be found.")
                }
                else -> {}
            }
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

}