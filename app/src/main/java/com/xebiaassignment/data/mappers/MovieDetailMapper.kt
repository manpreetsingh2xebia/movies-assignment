package com.xebiaassignment.data.mappers

import com.xebiaassignment.data.model.MovieDetailResponse
import com.xebiaassignment.data.model.MovieItem
import com.xebiaassignment.domain.model.MovieDetailData
import com.xebiaassignment.domain.model.PopularMoviesData

fun MovieDetailResponse.movieDetail(): MovieDetailData {
    return MovieDetailData(
        imagePath = this.posterPath ?: "",
        originalTitle = this.originalTitle ?: "",
        releaseDate = this.releaseDate ?: "",
        overview = this.overview ?: "",
        genres = this.genres?: emptyList()
    )
}