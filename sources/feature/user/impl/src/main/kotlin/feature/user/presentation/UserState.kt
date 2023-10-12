package feature.user.presentation

import base.compose.mvi.Effect
import base.compose.mvi.Event
import base.compose.mvi.Intent
import base.compose.mvi.State
import base.compose.navigation.Direction
import feature.user.domain.entity.User

data class UserState(
    val fieldName: String = "",
    val fieldSurname: String = "",
    val fieldEmail: String = "",
    val itUploading: Boolean = false,
    val user: User = User(id = "", name = "", surname = "", email = "", photoUrl = ""),
) : State

sealed class UserIntent : Intent {
    data class Init(val userId: String) : UserIntent()
    data object Save : UserIntent()
    data object Delete : UserIntent()
    data class ChangeName(val name: String) : UserIntent()
    data class ChangeSurname(val surname: String) : UserIntent()
    data class ChangeEmail(val email: String) : UserIntent()
}

sealed class UserEffect : Effect {
    data class DataLoaded(val user: User) : UserEffect()
    data class ChangeName(val name: String) : UserEffect()
    data class ChangeSurname(val surname: String) : UserEffect()
    data class ChangeEmail(val email: String) : UserEffect()
    data class ChangeUploading(val isUploading: Boolean) : UserEffect()
    data object NavigateBack : UserEffect()
}

sealed class UserEvent : Event {
    data class Navigate(val direction: Direction) : UserEvent()
}
