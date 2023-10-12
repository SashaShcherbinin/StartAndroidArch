package feature.user.domain.usecase

import feature.user.domain.entity.User

interface UpdateUserUseCase : suspend (User) -> Unit