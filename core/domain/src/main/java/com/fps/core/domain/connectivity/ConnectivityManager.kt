package com.fps.core.domain.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityManager {
    fun networkState(): Flow<NetworkConnectivityState>
}