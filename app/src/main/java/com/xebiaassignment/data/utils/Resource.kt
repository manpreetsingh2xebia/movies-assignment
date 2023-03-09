package com.xebiaassignment.data.utils

//data class Resource<out T>(val status : Status , val data : T? , val message : String?){
//
//    companion object{
//        fun <T> success(data : T) : Resource<T> {
//            return Resource(Status.SUCCESS , data , null)
//        }
//        fun <T> error(message: String , data : T ?) : Resource<T>{
//            return Resource(Status.ERROR , data , message)
//        }
//        fun <T> loading(data : T?) : Resource<T>{
//            return Resource(Status.LOADING , data , null)
//        }
//    }
//}

//enum class Status{
//    SUCCESS,
//    ERROR,
//    LOADING
//}

sealed class Resource<out T>{
    data class Success<out T>(val data : T) : Resource<T>()
    data class Error<out T>(val message: String) : Resource<T>()
    object Loading : Resource<Nothing>()
}

