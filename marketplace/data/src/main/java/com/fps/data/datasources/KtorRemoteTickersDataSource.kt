package com.fps.data.datasources

import com.fps.core.data.networking.get
import com.fps.core.domain.marketplace.CryptoPair
import com.fps.core.domain.marketplace.RemoteTickersDataSource
import com.fps.core.domain.util.DataError
import com.fps.core.domain.util.Result
import com.fps.core.domain.util.map
import com.fps.data.mappers.toCryptoPair
import com.fps.data.models.CryptoPairDto
import io.ktor.client.HttpClient

class KtorRemoteTickersDataSource(
    private val httpClient: HttpClient
) : RemoteTickersDataSource {
    override suspend fun getTickers(symbols: String): Result<List<CryptoPair>, DataError.Network> {
        return httpClient.get<List<List<String?>>>(
            route = "/tickers?symbols=$symbols",
        ).map { cryptoPairDtos ->
            cryptoPairDtos.map {
                CryptoPairDto(
                    symbol = it[0].orEmpty(),
                    bid = it[1]?.toDoubleOrNull(),
                    bidSize = it[2]?.toDoubleOrNull(),
                    ask = it[3]?.toDoubleOrNull(),
                    askSize = it[4]?.toDoubleOrNull(),
                    dailyChange = it[5]?.toDoubleOrNull(),
                    dailyChangePercentage = it[6]?.toDoubleOrNull(),
                    lastPrice = it[7]?.toFloatOrNull(),
                    volume = it[8]?.toDoubleOrNull(),
                    high = it[9]?.toDoubleOrNull(),
                    low = it[10]?.toDoubleOrNull()
                )
            }.map { it.toCryptoPair() }
        }
    }
}