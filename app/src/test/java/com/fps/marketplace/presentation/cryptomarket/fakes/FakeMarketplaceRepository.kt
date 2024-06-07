package com.fps.marketplace.presentation.cryptomarket.fakes

import com.fps.core.domain.marketplace.CryptoPair
import com.fps.core.domain.marketplace.MarketplaceRepository
import com.fps.core.domain.util.DataError
import com.fps.core.domain.util.Result
import kotlinx.coroutines.flow.flow

class FakeMarketplaceRepository : MarketplaceRepository {

    private var tickersResult: Result<List<CryptoPair>, DataError.Network> =
        Result.Success(emptyList())
    var getTickersCalled = false
        private set

    override suspend fun getTickers(): kotlinx.coroutines.flow.Flow<Result<List<CryptoPair>, DataError.Network>> =
        flow {
            getTickersCalled = true
            emit(tickersResult)
        }

    fun setTickersResult(result: Result<List<CryptoPair>, DataError.Network>) {
        tickersResult = result
    }
}