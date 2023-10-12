package feature.user.domain.repository

import feature.user.domain.entity.User
import feature.user.domain.entity.UserId
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(userId: UserId): Flow<Result<User>>

    fun getUsers(): Flow<Result<List<User>>>
}