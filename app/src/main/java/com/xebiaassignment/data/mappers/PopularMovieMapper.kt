package com.xebiaassignment.data.mappers

import com.xebiaassignment.data.model.MovieItem
import com.xebiaassignment.domain.model.PopularMoviesData

fun MovieItem.popularMovies(): PopularMoviesData {
    return PopularMoviesData(
        id = this.id ?: 0,
        imagePath = this.posterPath ?: "",
        originalTitle = this.originalTitle ?: "",
        voteCount = this.voteCount ?: 0,
        voteAverage = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate ?: "",
    )
}