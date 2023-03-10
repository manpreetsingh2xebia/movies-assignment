package com.xebiaassignment.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xebiaassignment.data.utils.ConnectionState
import com.xebiaassignment.data.utils.connectivityState
import com.xebiaassignment.presentation.moviedetail.MovieDetail
import com.xebiaassignment.presentation.movielist.MovieListEvents
import com.xebiaassignment.presentation.movielist.MovieListScreen
import com.xebiaassignment.presentation.movielist.MovieListVM
import com.xebiaassignment.presentation.ui.theme.XebiaAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val movieListVM: MovieListVM = hiltViewModel()
            ConnectionObserve(movieListVM)
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
            )
            val coroutineScope = rememberCoroutineScope()
            BackHandler {
                if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                        movieListVM.onEventChanged(MovieListEvents.ClearDetail)
                    }
                } else {
                    finish()
                }
            }

            XebiaAssignmentTheme {
                BottomSheetScaffold(
                    scaffoldState = bottomSheetScaffoldState,
                    sheetContent = {
                        MovieDetail(
                            movieListVM = movieListVM
                        ){
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                                movieListVM.onEventChanged(MovieListEvents.ClearDetail)
                            }
                        }
                    },
                    sheetPeekHeight = 0.dp,
                    sheetBackgroundColor = Color.Transparent
                ) {
                    MovieListScreen(
                        movieListVM = movieListVM,
                        redirectToDetail = {
                            movieListVM.onEventChanged(MovieListEvents.OnMovieDetail(it))
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
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
    ) {
        // This will call on every network state change
        val connection by connectivityState()
        val isConnected = connection == ConnectionState.Available
        Log.e("NETWORK", "connection${connection}")
        LaunchedEffect(key1 = isConnected, block = {
            if (isConnected) {
                movieListVM.onEventChanged(MovieListEvents.OnInternetConnectionChange(true))
            } else {
                movieListVM.onEventChanged(MovieListEvents.OnInternetConnectionChange(false))
            }
        })
    }

    /**
     * System's Back Press callback
     * */
    @Composable
    fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
        // Safely update the current `onBack` lambda when a new one is provided
        val currentOnBack by rememberUpdatedState(onBack)
        // Remember in Composition a back callback that calls the `onBack` lambda
        val backCallback = remember {
            object : OnBackPressedCallback(enabled) {
                override fun handleOnBackPressed() {
                    currentOnBack()
                }
            }
        }
        // On every successful composition, update the callback with the `enabled` value
        SideEffect {
            backCallback.isEnabled = enabled
        }
        val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
            "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
        }.onBackPressedDispatcher

        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner, backDispatcher) {
            backDispatcher.addCallback(lifecycleOwner, backCallback)
            // When the effect leaves the Composition, remove the callback
            onDispose {
                backCallback.remove()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    XebiaAssignmentTheme {

    }
}