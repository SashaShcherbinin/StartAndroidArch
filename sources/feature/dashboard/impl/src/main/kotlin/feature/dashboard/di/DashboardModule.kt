package feature.dashboard.di

import feature.dashboard.presentation.DashboardProcessor
import feature.dashboard.presentation.DashboardPublisher
import feature.dashboard.presentation.DashboardReducer
import feature.dashboard.presentation.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureDashboardModule(): Module = module {
    viewModelOf(::DashboardViewModel)
    factoryOf(::DashboardProcessor)
    factoryOf(::DashboardReducer)
    factoryOf(::DashboardPublisher)
}
