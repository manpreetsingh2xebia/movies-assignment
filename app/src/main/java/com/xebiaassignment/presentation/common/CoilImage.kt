package com.xebiaassignment.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun CoilImage(
    src : String,
    modifier : Modifier = Modifier,
    contentScale : ContentScale = ContentScale.Fit
){

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(src)
            .apply(
                block = fun ImageRequest.Builder.(){
                    memoryCachePolicy(policy = CachePolicy.ENABLED)
                }
            ).build()
    )

    Image(
        contentScale = contentScale,
        painter = painter,
        contentDescription = "img",
        modifier = modifier
    )

}