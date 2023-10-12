package feature.user.domain.usecase

import feature.user.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface GetUsersUseCase : () -> Flow<Result<List<User>>>