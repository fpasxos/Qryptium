package com.fps.marketplace.presentation.di

import com.fps.marketplace.presentation.cryptomarket.CryptoMarketViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val marketplaceViewModelModule = module {
    viewModelOf(::CryptoMarketViewModel)
}