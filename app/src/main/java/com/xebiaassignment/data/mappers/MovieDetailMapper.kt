package com.xebiaassignment.data.mappers

import com.xebiaassignment.data.model.MovieDetailResponse
import com.xebiaassignment.domain.model.MovieDetailData

fun MovieDetailResponse.movieDetail(): MovieDetailData {
    return MovieDetailData(
        imagePath = this.posterPath ?: "",
        originalTitle = this.originalTitle ?: "",
        releaseDate = this.releaseDate ?: "",
        overview = this.overview ?: "",
        genres = this.genres ?: emptyList()
    )
}