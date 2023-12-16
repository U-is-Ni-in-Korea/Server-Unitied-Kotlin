package universe.sparkle.application.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Repository
import universe.sparkle.domain.exception.BadRequest
import universe.sparkle.domain.exception.Unauthorized
import universe.sparkle.domain.gateway.UserDetailGateway
import universe.sparkle.domain.model.AuthenticationToken
import universe.sparkle.domain.model.toAuthenticationToken
import universe.sparkle.application.persistence.mapper.toDomain
import universe.sparkle.application.persistence.repository.UserRepository

@Repository
class UserDetailAdapter @Autowired constructor(
    private val userRepository: UserRepository,
) : UserDetailGateway, UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val userId = username?.toLong() ?: throw Unauthorized.UnsupportedToken()
        val user = userRepository.findByIdOrNull(userId)?.toDomain()
            ?: throw BadRequest.UserNotExistent()
        val userAuthenticationToken = user.toAuthenticationToken() ?: throw BadRequest.UserNotExistent()
        return UserDetail.of(userAuthenticationToken)
    }

    override fun loadUserById(userId: String?): AuthenticationToken {
        return (this.loadUserByUsername(userId) as? UserDetail)
            ?.toAuthenticationToken()
            ?: throw IllegalArgumentException("can not change userDetail interface to UserDetailModel")
    }
}
