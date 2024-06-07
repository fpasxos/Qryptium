package com.fps.qryptium.di

import com.fps.qryptium.Qryptium
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as Qryptium).applicationScope
    }
}