package feature.user.domain.usecase

import feature.user.domain.entity.User
import feature.user.domain.entity.UserId
import kotlinx.coroutines.flow.Flow

interface GetUserUseCase : (UserId) -> Flow<Result<User>>