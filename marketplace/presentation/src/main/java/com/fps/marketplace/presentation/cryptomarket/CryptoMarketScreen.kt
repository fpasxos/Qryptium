package com.fps.marketplace.presentation.cryptomarket

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.fps.core.presentation.designsystem.QryptiumTheme
import com.fps.core.presentation.designsystem.components.ConnectivityStatus
import com.fps.core.presentation.designsystem.components.ErrorScreen
import com.fps.core.presentation.designsystem.components.MainAppBackground
import com.fps.core.presentation.designsystem.components.QryptiumSearchView
import com.fps.marketplace.presentation.cryptomarket.components.CryptoItem
import com.fps.marketplace.presentation.cryptomarket.components.PullToRefreshLazyColumn
import org.koin.androidx.compose.koinViewModel

// We create this composable in order to use it in the app, but this composable calls another composable
// which does not include anything related to the navigation so this CryptomarketScreen can be tested
// in the future and not having to rely into anything else
@Composable
fun CryptoMarketScreenRoot(
    viewModel: CryptoMarketViewModel = koinViewModel()
) {
    CryptoMarketScreen(state = viewModel.state, onAction = { action ->
        when (action) {
            // these lambdas, are empty but if we had to navigate to another screen we would do it here
            CryptoMarketAction.OnPullToRefresh -> {}
            is CryptoMarketAction.OnSearch -> {}
            CryptoMarketAction.OnClearSearch -> {}
            CryptoMarketAction.OnRefreshAfterError -> {}
        }
        viewModel.onAction(action)
    })
}

@Composable
fun CryptoMarketScreen(
    state: CryptoMarketState, onAction: (CryptoMarketAction) -> Unit
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        MainAppBackground(
            modifier = Modifier
        ) {
            ConnectivityStatus(isConnected = state.isConnectedToNetwork)
            QryptiumSearchView(searchQuery = state.searchQuery) { query ->
                onAction(CryptoMarketAction.OnSearch(query))
            }
            if (state.isConnectedToNetwork) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (state.error == null) {
                            PullToRefreshLazyColumn(items = state.cryptoPairsFilteredList,
                                content = {
                                    CryptoItem(
                                        cryptoPairUi = it
                                    )
                                },
                                isRefreshing = state.isLoading,
                                onRefresh = {
                                    onAction(CryptoMarketAction.OnPullToRefresh)
                                })
                        } else {
                            val context = LocalContext.current
                            ErrorScreen(state.error.asString(context)) {
                                onAction(CryptoMarketAction.OnRefreshAfterError)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CryptoMarketScreenPreview() {
    QryptiumTheme {
        CryptoMarketScreen(state = CryptoMarketState(), onAction = {})
    }
}