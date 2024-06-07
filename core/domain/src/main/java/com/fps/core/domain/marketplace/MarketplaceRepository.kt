package com.fps.core.domain.marketplace

import com.fps.core.domain.util.DataError
import com.fps.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface MarketplaceRepository {
    suspend fun getTickers(): Flow<Result<List<CryptoPair>, DataError.Network>>
}