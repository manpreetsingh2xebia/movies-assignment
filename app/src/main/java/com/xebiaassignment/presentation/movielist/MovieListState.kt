package com.xebiaassignment.presentation.movielist

import com.xebiaassignment.domain.model.MovieDetailData
import com.xebiaassignment.domain.model.NowPlayingData
import com.xebiaassignment.domain.model.PopularMoviesData

/**
 *  State variables of Movie list view model
 * */
data class MovieListState(
    val showInterConnectionDialog: Boolean = false,
    val showLoader: Boolean = false,
    val showError: Boolean = false,
    val message: String = "",
    val showLoaderOnBottomSheet: Boolean = false,
    val movieId: Int ?= null,
    val nowPlayingList: List<NowPlayingData> = emptyList(),
    val popularMoviesList: List<PopularMoviesData> = emptyList(),
    val movieDetail: MovieDetailData = MovieDetailData(),
)
