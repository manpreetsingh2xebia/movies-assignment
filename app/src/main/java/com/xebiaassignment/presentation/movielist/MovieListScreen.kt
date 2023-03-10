package com.xebiaassignment.presentation.movielist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.xebiaassignment.BuildConfig
import com.xebiaassignment.R
import com.xebiaassignment.domain.model.NowPlayingData
import com.xebiaassignment.domain.model.PopularMoviesData
import com.xebiaassignment.presentation.common.AppDivider
import com.xebiaassignment.presentation.common.CircularProgressBar
import com.xebiaassignment.presentation.common.CoilImage
import com.xebiaassignment.presentation.common.LoadingAnimation
import com.xebiaassignment.presentation.ui.theme.BLACK

/**
 * Screen showing both playing and most popular movies
 * */
@Composable
fun MovieListScreen(
    movieListVM: MovieListVM?,
    redirectToDetail: (Int) -> Unit
) {
    val state = movieListVM?.state?.value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = BLACK.copy(alpha = 0.70f))
            ) {

                if (state?.showLoader == true) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation(
                            circleColor = Color.Yellow
                        )
                    }

                }else if (state?.showError == true) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            style = TextStyle(
                                color = Color.Red,
                                fontSize = dimensionResource(id = R.dimen.txt_size_16).value.sp
                            ),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.size_10dp))
                        )
                    }

                } else {
                    /** NOW PLAYING MOVIES UI */
                    PlayingNow(state) { redirectToDetail(it) }

                    /** MOST POPULAR MOVIES UI */
                    MostPopular(state) { redirectToDetail(it) }

                }
            }
        }

    }
}

@Composable
fun PlayingNow(state: MovieListState?, redirectToDetail: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

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
        LazyRow {
            itemsIndexed(state?.nowPlayingList ?: emptyList()) { index, item ->
                CellPlayingNow(item) {
                    redirectToDetail(item.id)
                }
            }
        }
    }
}

@Composable
fun MostPopular(state: MovieListState?, redirectToDetail: (Int) -> Unit) {
    Text(
        text = stringResource(id = R.string.most_popular),
        style = TextStyle(
            color = Color.Yellow,
            fontSize = dimensionResource(id = R.dimen.txt_size_16).value.sp
        ),
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.size_10dp))
    )
    LazyColumn {
        itemsIndexed(state?.popularMoviesList ?: emptyList()) { index, item ->
            CellPopular(item) {
                redirectToDetail(item.id)
            }
        }
    }
}


@Composable
fun CellPlayingNow(
    item: NowPlayingData,
    clickItem: () -> Unit
) {
    CoilImage(
        modifier = Modifier
            .clickable { clickItem() }
            .height(dimensionResource(id = R.dimen.size_250dp))
            .width(dimensionResource(id = R.dimen.size_120dp)),
        src = BuildConfig.IMAGE_URL + item.imagePath
    )
}

@Composable
fun CellPopular(
    item: PopularMoviesData,
    clickItem: () -> Unit
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BLACK.copy(alpha = 0.85f))
                .padding(dimensionResource(id = R.dimen.size_20dp))
                .clickable { clickItem() }
        ) {

            CoilImage(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.size_80dp))
                    .width(dimensionResource(id = R.dimen.size_80dp)),
                src = BuildConfig.IMAGE_URL + item.imagePath,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(0.8f)
            ) {
                Text(
                    text = item.originalTitle,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = dimensionResource(id = R.dimen.txt_size_16).value.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.size_10dp),
                            end = dimensionResource(id = R.dimen.size_5dp)
                        )
                )

                Text(
                    text = item.releaseDate,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = dimensionResource(id = R.dimen.txt_size_14).value.sp
                    ),
                    fontWeight = FontWeight.Medium,

                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.size_10dp),
                            top = dimensionResource(id = R.dimen.size_10dp)
                        )
                )

            }

            Box(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.size_10dp)
                    )
                    .weight(0.2f),
            ) {
                CircularProgressBar(
                    (item.voteAverage / 10).toFloat(),
                    item.voteAverage,
                )
            }
        }

        AppDivider(
            color = BLACK.copy(alpha = 0.40f),
            thickness = dimensionResource(id = R.dimen.size_10dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
