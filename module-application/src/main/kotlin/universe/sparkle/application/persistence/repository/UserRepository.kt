package universe.sparkle.application.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import universe.sparkle.application.persistence.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findBySnsAuthCode(snsAuthCode: String): UserEntity?
}
