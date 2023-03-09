package com.xebiaassignment.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.xebiaassignment.data.utils.ConnectionState
import com.xebiaassignment.data.utils.connectivityState
import com.xebiaassignment.presentation.movielist.MovieListEvents
import com.xebiaassignment.presentation.movielist.MovieListScreen
import com.xebiaassignment.presentation.movielist.MovieListVM
import com.xebiaassignment.presentation.ui.theme.XebiaAssignmentTheme
import com.xebiaassignment.presentation.utils.UiConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val movieListVM : MovieListVM = hiltViewModel()
            ConnectionObserve(movieListVM)
            XebiaAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // A surface container using the 'background' color from the theme
                    NavControllerComposable(
                        navHostController = navController,
                        movieListVM = movieListVM
                    )
                }
            }
        }
    }

    /** INTERNET CONNECTION CHECK */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    fun ConnectionObserve(
        movieListVM: MovieListVM
    ){
        // This will call on every network state change
        val connection by connectivityState()
        val isConnected = connection == ConnectionState.Available
        Log.e("NETWORK", "connection${connection}")
        LaunchedEffect(key1 = isConnected, block = {
            if(isConnected){
                movieListVM.onEventChanged(MovieListEvents.OnInternetConnectionChange(true))
            }else{
                movieListVM.onEventChanged(MovieListEvents.OnInternetConnectionChange(false))
            }
        })
    }

    @Composable
    fun NavControllerComposable(
        movieListVM: MovieListVM,
        navHostController: NavHostController
    ){
        NavHost(navHostController, startDestination = UiConstants.MOVIE_LIST_SCREEN ){
            composable(route = UiConstants.MOVIE_LIST_SCREEN){
                // Movie List screen
                MovieListScreen(
                    movieListVM = movieListVM,
                    redirectToDetail = {
                        navHostController.navigate(UiConstants.MOVIE_DETAIL_SCREEN+"/$it" )
                    }
                )
            }
            composable(
                route = UiConstants.MOVIE_DETAIL_SCREEN + "/{movieId}",
                arguments = listOf( navArgument("movieId"){ type = NavType.IntType} )
            ){
                val movieId = it.arguments?.getInt("movieId")
                // Movie detail screen

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    XebiaAssignmentTheme {
        Greeting("Android")
    }
}