package universe.sparkle.domain.exception

sealed class BadRequest(
    override val code: Int,
    override val message: String,
) : BusinessException(code, message)
