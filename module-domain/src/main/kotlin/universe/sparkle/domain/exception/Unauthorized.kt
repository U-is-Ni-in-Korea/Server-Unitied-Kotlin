package universe.sparkle.domain.exception

sealed class Unauthorized(
    code: Int,
    message: String,
) : BusinessException(code, message) {
    data class TokenNotExistent(
        override val message: String = "토큰 값이 존재하지 않습니다.",
    ) : Unauthorized(2001, message)

    data class ExpiredToken(
        override val message: String = "토큰이 만료 되었습니다.",
    ) : Unauthorized(2002, message)

    data class UnsupportedToken(
        override val message: String = "지원하지 않는 방식의 토큰 혹은 변조된 토큰을 사용한 경우입니다.",
    ) : Unauthorized(2003, message)
}
