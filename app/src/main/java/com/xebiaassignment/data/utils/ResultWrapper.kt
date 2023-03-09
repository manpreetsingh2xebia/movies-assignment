package com.xebiaassignment.data.utils

sealed class ResultWrapper<out T>{
    data class Success<out T>(val data : T): ResultWrapper<T>()
    data class GenericError(val code : Int? = null , val message : String?=null): ResultWrapper<Nothing>()
    data class NetworkError(val message : String): ResultWrapper<Nothing>()
}
