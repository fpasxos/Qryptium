package com.fps.marketplace.presentation.cryptomarket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fps.core.domain.connectivity.ConnectivityManager
import com.fps.core.domain.connectivity.NetworkConnectivityState
import com.fps.core.domain.marketplace.MarketplaceRepository
import com.fps.core.domain.util.Result
import com.fps.core.presentation.ui.asUiText
import com.fps.marketplace.presentation.cryptomarket.mappers.toCryptoPairUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CryptoMarketViewModel(
    private val marketplaceRepository: MarketplaceRepository,
    connectivityManager: ConnectivityManager
) : ViewModel() {

    var state by mutableStateOf(CryptoMarketState())
        private set

    var textQuery = ""
        private set

    private var tickerJob: Job? = null

    fun startTickers() {
        tickerJob?.cancel() // Cancel any existing ticker job
        tickerJob = viewModelScope.launch {
            while (true) {
                getTickers()
                delay(5000)
            }
        }
    }

    fun stopTickers() {
        tickerJob?.cancel()
        tickerJob = null
    }

    val networkStatus: StateFlow<NetworkConnectivityState> =
        connectivityManager.networkState().stateIn(
            initialValue = NetworkConnectivityState.Unavailable,
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )

    init {
        viewModelScope.launch {
            launch {
                networkStatus.collect {
                    state =
                        state.copy(isConnectedToNetwork = it == NetworkConnectivityState.Available)
                }
            }

            startTickers()
        }
    }

    fun onAction(action: CryptoMarketAction) {
        when (action) {
            CryptoMarketAction.OnPullToRefresh -> {
                viewModelScope.launch {
                    getTickers()
                }
            }

            is CryptoMarketAction.OnSearch -> {
                textQuery = action.query
                state = state.copy(
                    searchQuery = action.query,
                    cryptoPairsFilteredList = state.cryptoPairsList.filter {
                        it.name.contains(
                            action.query,
                            true
                        )
                    })
            }

            CryptoMarketAction.OnClearSearch -> {
                textQuery = ""
                state = state.copy(searchQuery = "")
            }

            CryptoMarketAction.OnRefreshAfterError -> {
                viewModelScope.launch {
                    state = state.copy(error = null)
                }
            }
        }
    }

    private suspend fun getTickers() {
        state = state.copy(isLoading = true)
        marketplaceRepository.getTickers().onEach { cryptoResult ->
            when (cryptoResult) {
                is Result.Error -> {
                    state =
                        state.copy(
                            isLoading = false,
                            error = cryptoResult.error.asUiText()
                        )
                }

                is Result.Success -> {
                    state = state.copy(
                        error = null,
                        isLoading = false,
                        cryptoPairsList = cryptoResult.data.map { it.toCryptoPairUi() },
                        cryptoPairsFilteredList = state.cryptoPairsList.filter {
                            it.name.contains(
                                textQuery,
                                true
                            )
                        })
                }
            }
        }.launchIn(viewModelScope)
    }
}