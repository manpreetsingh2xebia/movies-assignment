package com.xebiaassignment.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

@ExperimentalCoroutinesApi
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState -> trySend(connectionState) }

    connectivityManager.registerDefaultNetworkCallback(callback)

    val currentState = getCurrentState(connectivityManager)

    trySend(currentState)

    // Remove call back when it is not in use
    awaitClose {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }
}

fun getCurrentState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val connected = connectivityManager.allNetworks.any {
        connectivityManager.getNetworkCapabilities(it)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    return if (connected) ConnectionState.Available else ConnectionState.Unavailable
}

fun networkCallback(callback: (ConnectionState) -> Unit): NetworkCallback {
    return object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            callback(ConnectionState.Available)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            callback(ConnectionState.Unavailable)
        }

    }
}

val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentState(connectivityManager)
    }


@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {

    val context = LocalContext.current
    return produceState(
        initialValue = context.currentConnectivityState
    ) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}