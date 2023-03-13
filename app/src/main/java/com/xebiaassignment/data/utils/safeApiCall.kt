package com.xebiaassignment.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

/** Generic function for API Calling and handling response */

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    val msg = when (throwable) {
                        is UnknownHostException -> {
                            AppConstants.INTERNET_CONNECTION_ERROR
                        }
                        else -> {
                            throwable.message
                        }
                    }
                    ResultWrapper.NetworkError(message = msg ?: AppConstants.DEFAULT_ERROR)
                }
                is HttpException -> {
                    val code = throwable.code()
                    val msg = if(throwable.response()?.errorBody() != null){
                        val errorBody = throwable.response()?.errorBody()?.string()
                        val json = JSONObject(errorBody?:"")
                        if(json.has("status_message"))
                            json.getString("status_message")
                        else
                            "Something went wrong"
                    }else "Something went wrong"

                    ResultWrapper.GenericError(code = code, message = msg)
                }
                else -> {
                    ResultWrapper.GenericError(code = null, message = throwable.message)
                }
            }
        }
    }
}