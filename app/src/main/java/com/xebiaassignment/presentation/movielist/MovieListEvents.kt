package com.xebiaassignment.presentation.movielist

sealed class MovieListEvents{
    data class OnInternetConnectionChange( val available : Boolean) : MovieListEvents()
}