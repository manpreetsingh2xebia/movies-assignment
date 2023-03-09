package com.xebiaassignment.presentation.movielist

import com.xebiaassignment.domain.model.NowPlayingData

/**
 *  State variables of Movie list view model
 * */
data class MovieListState(
    val showInterConnectionDialog : Boolean = false,
    val nowPlayingList : List<NowPlayingData> = emptyList(),
)
