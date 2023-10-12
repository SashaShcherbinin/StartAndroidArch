package feature.dashboard.presentation

import base.compose.mvi.Effect
import base.compose.mvi.Event
import base.compose.mvi.Intent
import base.compose.mvi.State
import feature.user.domain.entity.User

data class DashboardState(
    val users: List<User> = emptyList(),
    val contentState: ContentState = ContentState.Loading,
    val errorResId: Int = 0,
) : State

sealed interface DashboardIntent : Intent {
    data object Init : DashboardIntent
}

sealed class DashboardEffect : Effect {
    data class UpdateState(val contentState: ContentState) : DashboardEffect()
    data class DataLoaded(val users: List<User>) : DashboardEffect()
}

sealed class DashboardEvent : Event {

}

sealed class ContentState {
    data object Loading : ContentState()
    data object Content : ContentState()
    data class Error(val messageResId: Int) : ContentState()
}
