package feature.user.domain.usecase

import kotlinx.coroutines.delay

class DeleteUserUseCaseImpl : DeleteUserUseCase {

    override suspend fun invoke(userId: UserId) {
        delay(2000)
    }
}
