package universe.sparkle.domain.exception

sealed class InternalServerError(
    override val code: Int,
    override val message: String,
) : BusinessException(code, message)
