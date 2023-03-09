package com.xebiaassignment.presentation.movielist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xebiaassignment.BuildConfig
import com.xebiaassignment.app.AssignmentApp
import com.xebiaassignment.data.utils.Resource
import com.xebiaassignment.domain.use_cases.UseCaseNwPlaying
import com.xebiaassignment.domain.use_cases.UseCasePopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * View model for managing state of movie list screen and
 * Providing data to ui using state
 * */
@HiltViewModel
class MovieListVM @Inject constructor(
    val useCaseNwPlaying: UseCaseNwPlaying,
    val useCasePopularMovies: UseCasePopularMovies
) : ViewModel() {

    // Avoiding state to update outside from this view model
    private val _movieListState = mutableStateOf(MovieListState())

    // Allowing UI to collect data from state
    var state: State<MovieListState> = _movieListState

    init {
        nowPlaying()
        popularMovies()
    }

    fun onEventChanged(event: MovieListEvents) {
        when (event) {
            is MovieListEvents.OnInternetConnectionChange -> {
                _movieListState.value = _movieListState.value.copy(
                    showInterConnectionDialog = event.available
                )
            }
            is MovieListEvents.LoaderEvent -> {
                _movieListState.value = _movieListState.value.copy(
                    showLoader = event.show
                )
            }
        }
    }

    /** API Calling for now playing movies list */
    private fun nowPlaying() {
        _movieListState.value = _movieListState.value.copy(
            showLoader = true
        )
        useCaseNwPlaying(
            apiKey = BuildConfig.API_KEY,
            lang = AssignmentApp.appLanguage,
            page = 1
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    _movieListState.value = _movieListState.value.copy(
                        nowPlayingList = it.data,
                        showLoader = false
                    )
                }
                is Resource.Error -> {
                    _movieListState.value = _movieListState.value.copy(
                        showLoader = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    /** API Calling for Popular movies list */
    private fun popularMovies() {
        useCasePopularMovies(
            apiKey = BuildConfig.API_KEY,
            lang = AssignmentApp.appLanguage,
            page = 1
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    _movieListState.value = _movieListState.value.copy(
                        popularMoviesList = it.data,
                        showLoader = false
                    )
                }
                is Resource.Error -> {
                    _movieListState.value = _movieListState.value.copy(
                        showLoader = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}