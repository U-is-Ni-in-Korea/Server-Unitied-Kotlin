package universe.sparkle.domain.usecase

import universe.sparkle.domain.model.AuthToken
import universe.sparkle.domain.model.User

interface BeIssuedAuthTokenInputBoundary {
    operator fun invoke(user: User): AuthToken
    operator fun invoke(user: User, accessExpiryPeriodDay: Long, refreshExpiryPeriodDay: Long): AuthToken

    companion object {
        const val ACCESS_TOKEN_SUBJECT = "AccessToken"
        const val CLAIMS_USER_ID = "userId"
    }
}
