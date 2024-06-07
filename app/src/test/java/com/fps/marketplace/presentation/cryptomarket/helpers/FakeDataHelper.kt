package com.fps.marketplace.presentation.cryptomarket.helpers

import com.fps.data.models.CryptoPairDto
import kotlin.random.Random

object FakeDataHelper {
    fun getFakeCryptoPairDto(): List<CryptoPairDto> {
        return generateRandomData()
    }

    private fun generateRandomSymbol(): String {
        val symbols = listOf("tBTCUSD", "tETHUSD", "tLTCUSD", "tXRPUSD", "tBCHUSD")
        return symbols.random()
    }

    private fun generateRandomData(): List<CryptoPairDto> {
        val cryptoPairs = mutableListOf<CryptoPairDto>()
        repeat(10) {
            cryptoPairs.add(getRandomCryptoPairDto())
        }
        return cryptoPairs
    }

    private fun getRandomCryptoPairDto(): CryptoPairDto {
        return CryptoPairDto(
            symbol = generateRandomSymbol(),
            bid = Random.nextDouble(50000.0, 100000.0),
            bidSize = Random.nextDouble(0.0, 10.0),
            ask = Random.nextDouble(50000.0, 100000.0),
            askSize = Random.nextDouble(0.0, 10.0),
            dailyChange = Random.nextDouble(-1000.0, 1000.0),
            dailyChangePercentage = Random.nextDouble(-10.0, 10.0),
            lastPrice = Random.nextDouble(50000.0, 100000.0).toFloat(),
            volume = Random.nextDouble(0.0, 1000.0),
            high = Random.nextDouble(50000.0, 100000.0),
            low = Random.nextDouble(50000.0, 100000.0)
        )
    }
}