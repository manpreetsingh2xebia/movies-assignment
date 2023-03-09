package com.xebiaassignment.domain.model

import com.xebiaassignment.data.model.GenresItem

data class MovieDetailData(
    val imagePath : String="",
    val originalTitle : String="",
    val releaseDate : String="",
    val overview : String="",
    val genres : List<GenresItem?> = emptyList(),
)
