package universe.sparkle.infrastructure.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import universe.sparkle.infrastructure.persistence.entity.UserEntity

interface UserJpaRepository : JpaRepository<UserEntity, Long>
