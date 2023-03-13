package com.xebiaassignment.app.data_sorcee

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.google.gson.JsonSyntaxException
import com.xebiaassignment.BuildConfig
import com.xebiaassignment.app.helper.FileReader
import com.xebiaassignment.data.data_source.ApiService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject

@HiltAndroidTest
class NowPlayingMovieTest {

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
    fun testGet_now_Movies_ifJsonSyntaxException() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)
        testDispatcher.scheduler.advanceUntilIdle()
        val response = apiService.nowPlaying(BuildConfig.API_KEY, "", 1)
        mockWebServer.takeRequest()
        assertThat(response).isEqualTo("[]")

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGet_now_Movies_ifResultIsNotEmpty() = runTest {
        //Arrange
        val mockResponse = MockResponse()
        val resourceContent = FileReader.readFileFromResource("/responsemovieslist.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(resourceContent)
        mockWebServer.enqueue(mockResponse)

        //Act
        testDispatcher.scheduler.advanceUntilIdle()
        val response = apiService.nowPlaying(BuildConfig.API_KEY, "", 1)
        mockWebServer.takeRequest()
        //Assertion
        assertThat(response.results?.isEmpty()).isEqualTo(false)
        assertThat(response.results!!.size)
            .isEqualTo(2)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGet_now_Movies_ifInternalServerError() = runTest {
        //Arrange
        val mockResponse = MockResponse()
        val resourceContent = FileReader.readFileFromResource("/responsemovieslist.json")
        mockResponse.setResponseCode(501)
        mockResponse.setBody(resourceContent)
        mockWebServer.enqueue(mockResponse)
        //Act
        try {
            testDispatcher.scheduler.advanceUntilIdle()
            apiService.nowPlaying(BuildConfig.API_KEY, "", 1)
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


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

}