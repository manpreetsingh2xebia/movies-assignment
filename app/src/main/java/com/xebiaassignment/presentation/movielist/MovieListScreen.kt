package com.xebiaassignment.presentation.movielist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.xebiaassignment.BuildConfig
import com.xebiaassignment.domain.model.NowPlayingData
import com.xebiaassignment.presentation.common.CoilImage
import com.xebiaassignment.R
import com.xebiaassignment.presentation.ui.theme.BLACK


@Composable
fun MovieListScreen (
    movieListVM : MovieListVM?,
    redirectToDetail : (Int) -> Unit
){
    val state = movieListVM?.state?.value
    Box(

    ) {
        Column() {
            /** TITLE BAR text */
            Text(
                text = stringResource(id = R.string.movies),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = dimensionResource(id = R.dimen.txt_size_18).value.sp
                ),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.size_10dp))
                    .fillMaxWidth()
            )

            /** NOW PLAYING MOVIES UI */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = BLACK.copy(alpha = 0.75f))
            ) {
                Text(
                    text = stringResource(id = R.string.playing_now),
                    style = TextStyle(
                        color = Color.Yellow,
                        fontSize = dimensionResource(id = R.dimen.txt_size_16).value.sp
                    ),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.size_10dp))
                )
                LazyRow(){
                    itemsIndexed(state?.nowPlayingList?: emptyList()){ index, item ->
                        CellPlayingNow(item){
                            redirectToDetail(item.id)
                        }
                    }
                }
            }

            /** MOST POPULAR MOVIES UI */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = BLACK.copy(alpha = 0.75f))
            ) {



            }
        }
    }
}

@Composable
fun CellPlayingNow(
    item: NowPlayingData,
    clickItem : () -> Unit
) {
    CoilImage(
        modifier = Modifier
            .clickable { clickItem() }
            .height(dimensionResource(id = R.dimen.size_250dp))
            .width(dimensionResource(id = R.dimen.size_120dp)),
        src = BuildConfig.IMAGE_URL+item.imagePath
    )
}
