package universe.sparkle.infrastructure.security

import universe.sparkle.domain.model.AuthenticationToken

fun UserDetail.toAuthenticationToken() = AuthenticationToken(
    id = this.id,
    nickname = this.username,
    image = this.image,
    couple = this.couple
)
