package com.fps.core.domain.connectivity

sealed class NetworkConnectivityState {
    data object Available : NetworkConnectivityState()
    data object Unavailable : NetworkConnectivityState()
    data object Disconnected : NetworkConnectivityState()
}