package feature.dashboard.presentation

import base.compose.mvi.MviViewModel
import base.compose.mvi.Processor
import base.compose.mvi.Publisher
import base.compose.mvi.Reducer
import base.logger.AppLog
import feature.dashboard.R
import feature.dashboard.presentation.DashboardEffect.DataLoaded
import feature.dashboard.presentation.DashboardEffect.UpdateState
import feature.user.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform

class DashboardViewModel(
    processor: DashboardProcessor,
    reducer: DashboardReducer,
    publisher: DashboardPublisher,
) : MviViewModel<DashboardIntent, DashboardEffect, DashboardEvent, DashboardState>(
    defaultState = DashboardState(),
    processor = processor,
    reducer = reducer,
    publisher = publisher,
) {
    init {
        process(DashboardIntent.Init)
    }
}

class DashboardProcessor(
    private val getUsersUseCase: GetUsersUseCase,
    private val appLog: AppLog
) : Processor<DashboardIntent, DashboardEffect, DashboardState> {

    override fun process(intent: DashboardIntent, state: DashboardState): Flow<DashboardEffect> =
        when (intent) {
            DashboardIntent.Init -> observerData()
        }

    private fun observerData() = getUsersUseCase()
        .onStart { UpdateState(ContentState.Loading) }
        .transform { users ->
            users.fold(
                onSuccess = {
                    emit(DataLoaded(it))
                    emit(UpdateState(ContentState.Content))
                },
                onFailure = {
                    appLog.e(it)
                    emit(UpdateState(ContentState.Error(R.string.dashboard_network_error)))
                }
            )
        }
}

class DashboardReducer : Reducer<DashboardEffect, DashboardState> {
    override fun reduce(effect: DashboardEffect, state: DashboardState): DashboardState {
        return when (effect) {
            is UpdateState -> state.copy(contentState = effect.contentState)
            is DataLoaded -> state.copy(users = effect.users)
        }
    }
}

class DashboardPublisher : Publisher<DashboardEffect, DashboardEvent> {
    override fun publish(event: DashboardEffect): DashboardEvent? {
        return when (event) {
            is UpdateState -> null
            is DataLoaded -> null
        }
    }
}
