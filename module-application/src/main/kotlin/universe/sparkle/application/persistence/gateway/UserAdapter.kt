package universe.sparkle.application.persistence.gateway

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import universe.sparkle.domain.exception.BadRequest
import universe.sparkle.domain.gateway.UserGateway
import universe.sparkle.domain.model.User
import universe.sparkle.application.persistence.entity.UserEntity
import universe.sparkle.application.persistence.mapper.toDomain
import universe.sparkle.application.persistence.mapper.toEntity
import universe.sparkle.application.persistence.repository.UserRepository

@Repository
internal class UserAdapter @Autowired constructor(
    private val userRepository: UserRepository,
) : UserGateway {

    override fun findUserById(userId: Long): User {
        val userEntity: UserEntity = userRepository.findByIdOrNull(userId)
            ?: throw BadRequest.UserNotExistent()
        return userEntity.toDomain()
    }

    override fun saveUser(user: User): User {
        return user.toEntity()
            .also { userEntity -> userRepository.save(userEntity) }
            .toDomain()
    }

    override fun findUserBySnsAuthCode(snsAuthCode: String): User? {
        return userRepository.findBySnsAuthCode(snsAuthCode)
            ?.toDomain()
    }
}
