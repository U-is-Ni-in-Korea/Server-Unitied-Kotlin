package universe.sparkle.domain.usecase

import universe.sparkle.domain.model.AuthenticationToken

interface ExtractTokenInputBoundary {
    operator fun invoke(token: String): AuthenticationToken
    fun getExpiredUserAuthenticationToken(token: String): AuthenticationToken
}
