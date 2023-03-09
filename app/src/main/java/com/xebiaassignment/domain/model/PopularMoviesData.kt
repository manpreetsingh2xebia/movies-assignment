package com.xebiaassignment.domain.model

data class PopularMoviesData(
    val id: Int,
    val imagePath: String,
    val originalTitle: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
)