package universe.sparkle.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String? = null,
    val coupleId: Long? = null,
)
