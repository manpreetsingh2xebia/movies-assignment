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
import androidx.navigation.compose.rememberNavController
import com.xebiaassignment.data.utils.ConnectionState
import com.xebiaassignment.data.utils.connectivityState
import com.xebiaassignment.presentation.movielist.MovieListEvents
import com.xebiaassignment.presentation.movielist.MovieListVM
import com.xebiaassignment.presentation.ui.theme.XebiaAssignmentTheme
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

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        NavControllerComposable(
                            navHostController = navController,
                            movieListVM = movieListVM
                        )
                    }
                }
            }
        }
    }

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