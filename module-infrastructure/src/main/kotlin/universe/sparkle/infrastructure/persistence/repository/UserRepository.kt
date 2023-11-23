package universe.sparkle.infrastructure.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import universe.sparkle.infrastructure.persistence.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long>, UserDslRepository {

    fun findBySnsAuthCode(snsAuthCode: String): UserEntity?
}
