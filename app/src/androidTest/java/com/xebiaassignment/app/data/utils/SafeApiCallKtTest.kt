package com.xebiaassignment.app.data.utils

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.xebiaassignment.app.helper.FileReader
import com.google.common.truth.Truth.assertThat
import com.xebiaassignment.BuildConfig
import com.xebiaassignment.data.data_source.ApiService
import com.xebiaassignment.data.utils.ResultWrapper
import com.xebiaassignment.data.utils.safeApiCall
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class SafeApiCallKtTest {

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


    @Test
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun test_If_safeApiCallMethod_Send_Success() = runTest{
        val mockResponse = MockResponse()
        val resourceContent = FileReader.readFileFromResource("/responsemovieslist.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(resourceContent)
        mockWebServer.enqueue(mockResponse)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = safeApiCall(testDispatcher) {
            apiService.popularMovies(BuildConfig.API_KEY, "", 1)
        }
        assertThat(result is ResultWrapper.Success).isEqualTo(true)
        assertThat((result as ResultWrapper.Success).data.results!!.size).isEqualTo(2)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_If_api_key_is_wrong_Send_Generic_error() = runTest{
        val mockResponse = MockResponse()
        val resourceContent = FileReader.readFileFromResource("/response_if_error.json")
        mockResponse.setResponseCode(401)
        mockResponse.setBody(resourceContent)
        mockWebServer.enqueue(mockResponse)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = safeApiCall(testDispatcher) {
            apiService.popularMovies(BuildConfig.API_KEY, "", 1)
        }
        assertThat(result is ResultWrapper.GenericError).isEqualTo(true)
        assertThat((result as ResultWrapper.GenericError).message).isEqualTo("Invalid API key: You must be granted a valid key.")
        assertThat(result.code).isEqualTo(401)

    }
}