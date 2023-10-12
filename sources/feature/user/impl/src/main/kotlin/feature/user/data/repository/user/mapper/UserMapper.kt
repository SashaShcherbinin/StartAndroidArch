package feature.user.data.repository.user.mapper

import feature.user.data.repository.user.dto.UserDto
import feature.user.domain.entity.User

fun UserDto.toDomain(): User = User(
    id = login.uuid,
    name = name.first,
    surname = name.last,
    photoUrl = picture.large,
    email = email,
)