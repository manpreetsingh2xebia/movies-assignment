package com.xebiaassignment.presentation.movielist

sealed class MovieListEvents {
    data class OnInternetConnectionChange(val available: Boolean) : MovieListEvents()
    data class LoaderEvent(val show: Boolean) : MovieListEvents()
    data class OnMovieDetail(val movieId: Int)  : MovieListEvents()
    data class SetMovieId(val movieId: Int) : MovieListEvents()
    object ClearDetail : MovieListEvents()
}
