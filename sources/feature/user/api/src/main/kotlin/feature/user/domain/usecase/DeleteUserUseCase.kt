package feature.user.domain.usecase


typealias UserId = String

interface DeleteUserUseCase : suspend (UserId) -> Unit