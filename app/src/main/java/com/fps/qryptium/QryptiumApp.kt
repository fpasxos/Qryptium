package com.fps.qryptium

import android.app.Application
import com.fps.core.data.di.coreDataModule
import com.fps.data.di.marketplaceDataModule
import com.fps.marketplace.presentation.di.marketplaceViewModelModule
import com.fps.qryptium.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Qryptium : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@Qryptium)
            modules(
                marketplaceDataModule,
                marketplaceViewModelModule,
                appModule,
                coreDataModule
            )
        }
    }
}