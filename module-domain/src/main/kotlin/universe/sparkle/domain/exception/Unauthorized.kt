package universe.sparkle.domain.exception

sealed class Unauthorized(
    override val code: Int,
    override val message: String,
) : BusinessException(code, message)
