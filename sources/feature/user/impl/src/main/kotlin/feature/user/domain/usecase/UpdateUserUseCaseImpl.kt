package feature.user.domain.usecase

import feature.user.domain.entity.User
import kotlinx.coroutines.delay

class UpdateUserUseCaseImpl : UpdateUserUseCase {

    override suspend fun invoke(user: User) {
        delay(2000)
    }
}
