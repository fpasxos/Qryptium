@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.fps.core.data.di

import com.fps.core.data.networking.ConnectivityManagerImpl
import com.fps.core.data.networking.HttpClientFactory
import com.fps.core.domain.connectivity.ConnectivityManager
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }

    single<ConnectivityManager> {
        ConnectivityManagerImpl(get())
    }
}