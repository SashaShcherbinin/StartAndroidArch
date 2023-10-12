package feature.splash.presentation

import base.compose.mvi.MviViewModel
import base.compose.mvi.Processor
import base.compose.mvi.Publisher
import base.compose.mvi.Reducer
import base.compose.navigation.Forward
import feature.dashboard.navigation.SCREEN_DASHBOARD
import feature.splash.presentation.navigation.SCREEN_SPLASH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SplashViewModel(
    processor: SplashProcessor,
    reducer: SplashReducer,
    publisher: SplashPublisher,
) : MviViewModel<SplashIntent, SplashEffect, SplashEvent, SplashState>(
    defaultState = SplashState,
    processor = processor,
    reducer = reducer,
    publisher = publisher,
) {
    init {
        process(SplashIntent.Init)
    }
}

class SplashProcessor : Processor<SplashIntent, SplashEffect, SplashState> {
    override fun process(intent: SplashIntent, state: SplashState): Flow<SplashEffect> =
        when (intent) {
            SplashIntent.Init -> {
                flowOf(SplashEffect.NavigateToDashboard)
            }
        }
}

class SplashReducer : Reducer<SplashEffect, SplashState> {
    override fun reduce(effect: SplashEffect, state: SplashState): SplashState? =
        when (effect) {
            SplashEffect.NavigateToDashboard -> null
        }
}

class SplashPublisher : Publisher<SplashEffect, SplashEvent> {
    override fun publish(event: SplashEffect): SplashEvent =
        when (event) {
            SplashEffect.NavigateToDashboard -> SplashEvent.Navigate(
                Forward(
                    route = SCREEN_DASHBOARD,
                    popUpRoute = SCREEN_SPLASH,
                    inclusive = true
                )
            )
        }
}
