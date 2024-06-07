package com.fps.core.domain.marketplace

import com.fps.core.domain.util.DataError
import com.fps.core.domain.util.Result

interface RemoteTickersDataSource {
    suspend fun getTickers(symbols: String): Result<List<CryptoPair>, DataError.Network>
}