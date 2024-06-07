package com.fps.data.di

import com.fps.core.data.marketplace.MarketplaceRepositoryImpl
import com.fps.core.domain.marketplace.MarketplaceRepository
import com.fps.core.domain.marketplace.RemoteTickersDataSource
import com.fps.data.datasources.KtorRemoteTickersDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val marketplaceDataModule = module {
    singleOf(::MarketplaceRepositoryImpl).bind<MarketplaceRepository>()
    singleOf(::KtorRemoteTickersDataSource).bind<RemoteTickersDataSource>()
}