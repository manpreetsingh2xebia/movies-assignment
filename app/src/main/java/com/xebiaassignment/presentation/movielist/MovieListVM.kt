package com.xebiaassignment.presentation.movielist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xebiaassignment.BuildConfig
import com.xebiaassignment.app.AssignmentApp
import com.xebiaassignment.data.data_source.ApiService
import com.xebiaassignment.data.utils.Resource
import com.xebiaassignment.domain.use_cases.UseCaseNwPlaying
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
    val useCaseNwPlaying: UseCaseNwPlaying
) : ViewModel() {

    // Avoiding state to update outside from this view model
    private val _movieListState = mutableStateOf(MovieListState())
    // Allowing UI to collect data from state
    var state : State<MovieListState> = _movieListState

    init {
        nowPlaying()
    }

    fun onEventChanged(event : MovieListEvents){
        when(event){
            is MovieListEvents.OnInternetConnectionChange -> {
                _movieListState.value = _movieListState.value.copy(
                    showInterConnectionDialog = event.available
                )
            }
            else -> {

            }
        }
    }

    /** API Calling for now playing movies list */
    private fun nowPlaying(){
        useCaseNwPlaying(
            apiKey = BuildConfig.API_KEY,
            lang = AssignmentApp.appLanguage,
            page = 1
        ).onEach {
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _movieListState.value = _movieListState.value.copy(
                        nowPlayingList = it.data
                    )
                }
                is Resource.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

}