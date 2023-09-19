package universe.sparkle.domain.usecase

import universe.sparkle.domain.model.AuthToken
import universe.sparkle.domain.model.User

interface BeIssuedAuthTokenInputBoundary {
    operator fun invoke(user: User): AuthToken
    operator fun invoke(user: User, accessExpiryPeriodDay: Long, refreshExpiryPeriodDay: Long): AuthToken
}
