package feature.splash.di

import feature.splash.presentation.SplashProcessor
import feature.splash.presentation.SplashPublisher
import feature.splash.presentation.SplashReducer
import feature.splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureSplashModule(): Module = module {
    factoryOf(::SplashProcessor)
    factoryOf(::SplashReducer)
    factoryOf(::SplashPublisher)
    viewModelOf(::SplashViewModel)
}
