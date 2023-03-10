package com.xebiaassignment.presentation.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.xebiaassignment.BuildConfig
import com.xebiaassignment.R
import com.xebiaassignment.presentation.common.CoilImage
import com.xebiaassignment.presentation.common.LoadingAnimation
import com.xebiaassignment.presentation.movielist.MovieListVM


/**
 * Bottom Sheet content for showing detail of particular movie
 * */

@Composable
fun MovieDetail(
    movieListVM: MovieListVM,
    onCrossClick: () -> Unit
) {
    val state = movieListVM.state.value

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray.copy(alpha = 0.90f)),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.size_16dp),
                    end = dimensionResource(id = R.dimen.size_16dp)
                ),
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "cross icon",
                tint = Color.White,
                modifier = Modifier.clickable { onCrossClick() }
            )
        }

        if (state.showLoaderOnBottomSheet) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(
                    circleColor = Color.Yellow
                )
            }

        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.size_18dp))
                ) {
                    // Movie poster
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CoilImage(
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.size_300dp))
                                .width(dimensionResource(id = R.dimen.size_180dp)),
                            src = BuildConfig.IMAGE_URL + state.movieDetail.imagePath
                        )
                        // Text showing Original title of the movie
                        Text(
                            text = state.movieDetail.originalTitle,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = dimensionResource(id = R.dimen.txt_size_20).value.sp
                            ),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    top = dimensionResource(id = R.dimen.size_10dp)
                                )
                        )

                        // Text showing release date of the movie
                        Text(
                            text = state.movieDetail.releaseDate,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = dimensionResource(id = R.dimen.txt_size_14).value.sp
                            ),
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(
                                    top = dimensionResource(id = R.dimen.size_10dp)
                                )
                        )
                    }

                    // Text showing Overview heading
                    Text(
                        text = stringResource(id = R.string.overview),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = dimensionResource(id = R.dimen.txt_size_20).value.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.size_10dp)
                            )
                    )

                    // Text showing Overview of the movie
                    Text(
                        text = state.movieDetail.overview,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = dimensionResource(id = R.dimen.txt_size_14).value.sp
                        ),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.size_10dp)
                            )
                    )

                    // List view showing all genres of the movie
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        itemsIndexed(state.movieDetail.genres) { index, item ->
                            Text(
                                text = item?.name ?: "",
                                style = TextStyle(
                                    color = Color.DarkGray,
                                    fontSize = dimensionResource(id = R.dimen.txt_size_14).value.sp
                                ),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .padding(
                                        dimensionResource(id = R.dimen.size_10dp)
                                    )
                                    .background(color = Color.White)
                                    .padding(
                                        horizontal = dimensionResource(id = R.dimen.size_10dp),
                                        vertical = dimensionResource(id = R.dimen.size_6dp)
                                    )
                            )
                        }
                    }
                }
            }
        }

    }
}