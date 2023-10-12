package com.start

import android.app.Application
import base.data.module.networkModule
import base.logger.di.loggerModule
import feature.dashboard.di.featureDashboardModule
import feature.splash.di.featureSplashModule
import feature.user.di.featureUserModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val koin = KoinAndroidApplication.create(this)
            .androidContext(this)
            .androidLogger(Level.ERROR)
            .modules(
                loggerModule(),
                networkModule(),
                featureSplashModule(),
                featureDashboardModule(),
                featureUserModule(),
            )
        startKoin(koin)
    }

}