@file:Suppress("OPT_IN_USAGE")

package feature.user.presentation

import androidx.lifecycle.SavedStateHandle
import base.compose.mvi.MviViewModel
import base.compose.mvi.Processor
import base.compose.mvi.Publisher
import base.compose.mvi.Reducer
import base.compose.navigation.Back
import base.logger.AppLog
import feature.user.domain.usecase.DeleteUserUseCase
import feature.user.domain.usecase.GetUserUseCase
import feature.user.domain.usecase.UpdateUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

const val EXTRA_USER_ID = "userId"

class UserViewModel(
    savedStateHandle: SavedStateHandle,
    processor: UserProcessor,
    reducer: UserReducer,
    publisher: UserPublisher,
) : MviViewModel<UserIntent, UserEffect, UserEvent, UserState>(
    defaultState = UserState(),
    processor = processor,
    reducer = reducer,
    publisher = publisher,
) {

    init {
        val userId = savedStateHandle.get<String>(EXTRA_USER_ID)!!
        process(UserIntent.Init(userId))
    }
}

class UserProcessor(
    private val getUserUseCase: GetUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val appLog: AppLog,
) :
    Processor<UserIntent, UserEffect, UserState> {
    override fun process(intent: UserIntent, state: UserState): Flow<UserEffect> = when (intent) {
        is UserIntent.Init -> {
            getUserUseCase(intent.userId)
                .map { userResult ->
                    userResult.fold(
                        onSuccess = { user ->
                            UserEffect.DataLoaded(user)
                        },
                        onFailure = { e ->
                            appLog.e(e = e)
                            UserEffect.NavigateBack
                        }
                    )
                }
        }

        is UserIntent.Save -> {
            flow {
                emit(UserEffect.ChangeUploading(true))
                state.user.copy(
                    name = state.fieldName,
                    surname = state.fieldSurname,
                    email = state.fieldEmail,
                ).let { user ->
                    updateUserUseCase(user)
                }
                emit(UserEffect.ChangeUploading(false))
                emit(UserEffect.NavigateBack)
            }
        }

        is UserIntent.Delete -> {
            flow {
                emit(UserEffect.ChangeUploading(true))
                deleteUserUseCase(state.user.id)
                emit(UserEffect.ChangeUploading(false))
                emit(UserEffect.NavigateBack)
            }
        }

        is UserIntent.ChangeName -> {
            flowOf(UserEffect.ChangeName(intent.name))
        }

        is UserIntent.ChangeSurname -> {
            flowOf(UserEffect.ChangeSurname(intent.surname))
        }

        is UserIntent.ChangeEmail -> {
            flowOf(UserEffect.ChangeEmail(intent.email))
        }
    }
}

class UserReducer : Reducer<UserEffect, UserState> {
    override fun reduce(effect: UserEffect, state: UserState): UserState {
        return when (effect) {
            is UserEffect.DataLoaded -> {
                state.copy(
                    user = effect.user,
                    fieldName = effect.user.name,
                    fieldSurname = effect.user.surname,
                    fieldEmail = effect.user.email,
                )
            }

            is UserEffect.ChangeUploading -> {
                state.copy(itUploading = effect.isUploading)
            }

            is UserEffect.ChangeEmail -> {
                state.copy(fieldEmail = effect.email)
            }

            is UserEffect.ChangeName -> {
                state.copy(fieldName = effect.name)
            }

            is UserEffect.ChangeSurname -> {
                state.copy(fieldSurname = effect.surname)
            }

            is UserEffect.NavigateBack -> {
                state
            }
        }
    }
}

class UserPublisher : Publisher<UserEffect, UserEvent> {
    override fun publish(event: UserEffect): UserEvent? {
        return when (event) {
            is UserEffect.NavigateBack -> {
                return UserEvent.Navigate(Back)
            }

            is UserEffect.ChangeEmail -> null
            is UserEffect.ChangeName -> null
            is UserEffect.ChangeSurname -> null
            is UserEffect.ChangeUploading -> null
            is UserEffect.DataLoaded -> null
        }
    }
}
