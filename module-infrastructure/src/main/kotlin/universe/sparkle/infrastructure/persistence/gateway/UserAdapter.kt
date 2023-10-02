package universe.sparkle.infrastructure.persistence.gateway

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import universe.sparkle.domain.exception.BadRequest
import universe.sparkle.domain.gateway.UserGateway
import universe.sparkle.domain.model.User
import universe.sparkle.infrastructure.persistence.entity.UserEntity
import universe.sparkle.infrastructure.persistence.mapper.toDomain
import universe.sparkle.infrastructure.persistence.mapper.toEntity
import universe.sparkle.infrastructure.persistence.repository.UserJpaRepository

@Repository
internal class UserAdapter @Autowired constructor(
    private val userJpaRepository: UserJpaRepository,
) : UserGateway {

    override fun findUserById(userId: Long): User {
        val userEntity: UserEntity = userJpaRepository.findByIdOrNull(userId)
            ?: throw BadRequest.UserNotExistent()
        return userEntity.toDomain()
    }

    override fun saveUser(user: User): User {
        return user.toEntity()
            .also { userEntity -> userJpaRepository.save(userEntity) }
            .toDomain()
    }

    override fun findUserBySnsAuthCode(snsAuthCode: String): User? {
        return userJpaRepository.findBySnsAuthCode(snsAuthCode)
            ?.toDomain()
    }
}
