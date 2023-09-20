package universe.sparkle.infrastructure.persistence.mapper

import universe.sparkle.domain.model.User
import universe.sparkle.infrastructure.persistence.entity.UserEntity
import java.lang.IllegalArgumentException

fun UserEntity.toDomain() = User(
    id = this.id ?: throw IllegalArgumentException("ID has not been initialized yet"),
    snsType = this.snsType,
    snsAuthCode = this.snsAuthCode,
    nickname = this.nickname,
    image = this.image,
    fcmToken = this.fcmToken,
)

fun User.toEntity() = UserEntity(
    id = this.id,
    snsType = this.snsType,
    snsAuthCode = this.snsAuthCode,
    nickname = this.nickname,
    image = this.image,
    fcmToken = this.fcmToken,
)
