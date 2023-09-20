package universe.sparkle.domain.exception

sealed class NotFound(
    override val code: Int,
    override val message: String,
) : BusinessException(code, message)
