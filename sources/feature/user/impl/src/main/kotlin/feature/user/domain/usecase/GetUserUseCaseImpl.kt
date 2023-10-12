package feature.user.domain.usecase

import feature.user.domain.entity.User
import feature.user.domain.entity.UserId
import feature.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCaseImpl(private val userRepository: UserRepository) : GetUserUseCase {

    override fun invoke(userId: UserId): Flow<Result<User>> = userRepository.getUser(userId)
}
