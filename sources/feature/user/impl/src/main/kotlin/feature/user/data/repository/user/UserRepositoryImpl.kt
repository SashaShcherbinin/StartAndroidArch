package feature.user.data.repository.user

import base.storage.common.cashe.CachePolicy
import base.storage.common.storage.LocalStorage
import feature.user.data.repository.user.dto.ResultDto
import feature.user.data.repository.user.mapper.toDomain
import feature.user.data.repository.user.service.UserApi
import feature.user.domain.entity.User
import feature.user.domain.entity.UserId
import feature.user.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class UserRepositoryImpl(private val httpClient: HttpClient) : UserRepository {

    private val userLocalStorage = LocalStorage<UserId, User>(
        maxElements = 1,
        cachePolicy = CachePolicy.create(10, TimeUnit.SECONDS), network = { userId ->
            httpClient.get {
                url(UserApi.GET_USERS)
            }.body<ResultDto>().users.map { it.toDomain() }.find { it.id == userId }!!
        })

    private val usersLocalStorage = LocalStorage<Unit, List<User>>(
        maxElements = 1,
        cachePolicy = CachePolicy.create(10, TimeUnit.SECONDS), network = {
            httpClient.get {
                url(UserApi.GET_USERS)
            }.body<ResultDto>().users.map { it.toDomain() }
        })

    override fun getUser(userId: UserId): Flow<Result<User>> = userLocalStorage.get(userId)

    override fun getUsers(): Flow<Result<List<User>>> = usersLocalStorage.get(Unit)
}