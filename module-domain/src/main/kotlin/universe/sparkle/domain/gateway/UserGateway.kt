package universe.sparkle.domain.gateway

import universe.sparkle.domain.model.User

interface UserGateway {
    fun findUserById(userId: Long): User
    fun saveUser(user: User)
}
