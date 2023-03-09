package com.xebiaassignment.data.mappers

import com.xebiaassignment.data.model.MovieItem
import com.xebiaassignment.domain.model.NowPlayingData

fun MovieItem.nowPlaying() : NowPlayingData {
    return NowPlayingData(
        id = this.id?:0,
        imagePath = this.posterPath?:"",
    )
}