package com.xebiaassignment.presentation.movielist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.xebiaassignment.data.data_source.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListVM @Inject constructor(
    val apiService: ApiService
) : ViewModel() {

    private val _movieListState = mutableStateOf(MovieListState())
    var state : State<MovieListState> = _movieListState

    fun onEventChanged(event : MovieListEvents){
        when(event){
            is MovieListEvents.OnInternetConnectionChange -> {
                _movieListState.value = _movieListState.value.copy(
                    showInterConnectionDialog = event.available
                )
                Log.e("NETWORK", " ------------ ${state.value.showInterConnectionDialog}")
            }
            else -> {

            }
        }
    }

}