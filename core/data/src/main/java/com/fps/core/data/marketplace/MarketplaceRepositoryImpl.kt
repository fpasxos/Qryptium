package com.fps.core.data.marketplace

import com.fps.core.domain.marketplace.CryptoPair
import com.fps.core.domain.marketplace.MarketplaceRepository
import com.fps.core.domain.marketplace.RemoteTickersDataSource
import com.fps.core.domain.util.DataError
import com.fps.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarketplaceRepositoryImpl(
    private val remoteTickersDataSource: RemoteTickersDataSource
) : MarketplaceRepository {
    override suspend fun getTickers(): Flow<Result<List<CryptoPair>, DataError.Network>> = flow {
        val tickersResult = remoteTickersDataSource.getTickers(
            "tBTCUSD,tETHUSD,tCHSB:USD,tLTCUSD,tXRPUSD,tDSHUSD,tRRTUSD,tEOSUSD,tSANUSD,tDATUSD,tSNTUSD,tDOGE:USD,tLUNA:USD,tMATIC:USD,tNEXO:USD,tOCEAN:USD,tBEST:USD,tAAVE:USD,tPLUUSD,tFILUSD"
        )
        emit(
            when (tickersResult) {
                is Result.Success -> Result.Success(tickersResult.data)
                is Result.Error -> Result.Error(tickersResult.error)
            }
        )
    }
}