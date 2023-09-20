package universe.sparkle.domain.gateway

import universe.sparkle.domain.model.AuthenticationToken

interface UserDetailGateway {
    fun loadUserById(userId: String?): AuthenticationToken
}
