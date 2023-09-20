package universe.sparkle.domain.model

data class AuthenticationToken(
    val id: Long,
    val nickname: String?,
    val image: String?,
)
