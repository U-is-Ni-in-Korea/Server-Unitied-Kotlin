package universe.sparkle.infrastructure.persistence.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import universe.sparkle.infrastructure.persistence.entity.QUserEntity
import universe.sparkle.infrastructure.persistence.entity.UserEntity

@Repository
class UserSupportRepository @Autowired constructor(
    private val queryFactory: JPAQueryFactory,
) : UserDslRepository {
    fun findUserByIdJoinCouple(userId: Long): UserEntity? {
        return queryFactory.selectFrom(QUserEntity.userEntity)
            .where(QUserEntity.userEntity.id.eq(userId))
            .fetchOne()
    }
}
